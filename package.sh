mkdir target/jar

cp -rf plugin.yml target/jar/
cp -rf target/classes/* target/jar/
cd target/jar
zip -0r innova.jar *
cd ../..
cp target/jar/innova.jar .
rm -rf target/jar
