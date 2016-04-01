cd ../ahenk-usb
dpkg-buildpackage
cd ..
rm -rf README.md
rm -rf *.changes
rm -rf *.dsc
rm -rf *.tar.gz
mv  *.deb target/
