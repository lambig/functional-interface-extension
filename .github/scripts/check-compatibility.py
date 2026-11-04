"""Check the published API and execute an old compiled client with the candidate JAR."""
import hashlib
import os
from pathlib import Path
import subprocess
import sys
import urllib.request
from zipfile import ZipFile
import xml.etree.ElementTree as ET

candidate = Path(sys.argv[1]).resolve()
pom = candidate.with_suffix('.pom')
ns = {'m': 'http://maven.apache.org/POM/4.0.0'}
assert not ET.parse(pom).findall('.//m:dependency', ns), 'Unexpected POM dependencies'
work = Path('build/compatibility').resolve()
work.mkdir(parents=True, exist_ok=True)
baseline = work / 'functional-interface-extension-1.1.2.jar'
url = ('https://repo.maven.apache.org/maven2/io/github/lambig/'
       'functional-interface-extension/1.1.2/functional-interface-extension-1.1.2.jar')
with urllib.request.urlopen(url, timeout=60) as response:
    baseline.write_bytes(response.read())
assert hashlib.sha256(baseline.read_bytes()).hexdigest() == (
    'cd264bcc6afb6d4cad4be3cf310fd182bf58568a5945247888197a6b2e82522f'), 'Baseline digest changed'
with urllib.request.urlopen(url + '.sha1', timeout=60) as response:
    assert hashlib.sha1(baseline.read_bytes()).hexdigest() == response.read().decode().strip()

def classes(jar):
    with ZipFile(jar) as archive:
        names = sorted(n for n in archive.namelist() if n.endswith('.class'))
        for name in names:
            assert int.from_bytes(archive.read(name)[6:8], 'big') == 52, name
    return [name[:-6].replace('/', '.') for name in names]

java_home = Path(os.environ['JAVA_HOME'])
candidate_classes = classes(candidate)
assert candidate_classes == classes(baseline), 'Class set changed'
assert candidate_classes, 'No bytecode'

def api(jar):
    result = subprocess.check_output(
        [str(java_home / 'bin/javap'), '-public', '-s', '-classpath', str(jar),
         *candidate_classes], text=True)
    return '\n'.join(line for line in result.splitlines() if not line.startswith('Compiled from'))

old_api, new_api = api(baseline), api(candidate)
(work / 'baseline-api.txt').write_text(old_api, encoding='utf-8')
(work / 'candidate-api.txt').write_text(new_api, encoding='utf-8')
assert old_api == new_api, 'Public signatures changed; inspect build/compatibility/*-api.txt'
client = work / 'client'
client.mkdir(exist_ok=True)
subprocess.run([str(java_home / 'bin/javac'), '--release', '8', '-encoding', 'UTF-8',
                '-classpath', str(baseline), '-d', str(client),
                'example/src/main/java/example/OrderExample.java'], check=True)
runtime = Path(os.environ.get('TEST_JAVA_HOME', os.environ['JAVA_HOME'])) / 'bin/java'
for jar in (baseline, candidate):
    subprocess.run([str(runtime), '-cp', os.pathsep.join((str(client), str(jar))),
                    'example.OrderExample'], check=True)
print(f'API signatures unchanged: {len(candidate_classes)} classes; bytecode=52')
print(f'Baseline SHA256: {hashlib.sha256(baseline.read_bytes()).hexdigest()}')
print('Old compiled client passed with both baseline and candidate; POM dependencies=0')
