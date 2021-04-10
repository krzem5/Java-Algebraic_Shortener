import os
import subprocess
import sys
import zipfile



if (os.path.exists("build")):
	dl=[]
	for r,ndl,fl in os.walk("build"):
		dl=[os.path.join(r,k) for k in ndl]+dl
		for f in fl:
			os.remove(os.path.join(r,f))
	for k in dl:
		os.rmdir(k)
else:
	os.mkdir("build")
cd=os.getcwd()
os.chdir("src")
jfl=[]
for r,_,fl in os.walk("."):
	for f in fl:
		if (f[-5:]==".java"):
			jfl.append(os.path.join(r,f))
if (subprocess.run(["javac","-d","../build"]+jfl).returncode!=0):
	sys.exit(1)
os.chdir(cd)
with zipfile.ZipFile("build/algebraic_shortener.jar","w") as zf:
	print("Writing: META-INF/MANIFEST.MF")
	zf.write("manifest.mf",arcname="META-INF/MANIFEST.MF")
	for r,_,fl in os.walk("build"):
		for f in fl:
			if (f[-6:]==".class"):
				print(f"Writing: {os.path.join(r,f)[6:].replace(chr(92),'/')}")
				zf.write(os.path.join(r,f),os.path.join(r,f)[6:])
if ("--run" in sys.argv):
	subprocess.run(["java","-jar","build/algebraic_shortener.jar"])
