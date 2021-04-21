hg clone http://hg.openjdk.java.net/code-tools/asmtools
cd asmtools/build
ant
#build jar in asmtools-7.0-build/release/lib/asmtools.jar by default
echo '
public class Foo {
  javac Foo.java
  public static void main(String[] args) {
  boolean flag = true;
  if (flag) System.out.println("Hello, Java!");
  if (flag == true) System.out.println("Hello, JVM!");}
}' > Foo.java
javac Foo.java
java Foo
java -cp /path/to/asmtools.jar org.openjdk.asmtools.jdis.Main Foo.class > Foo.jasm.1
awk 'NR==1,/iconst_1/{sub(/iconst_1/, "iconst_2")} 1' Foo.jasm.1 > Foo.jasm
java -cp /path/to/asmtools.jar org.openjdk.asmtools.jasm.Main Foo.jasm
java Foo


