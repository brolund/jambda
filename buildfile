require 'buildr/scala'
# Generated by Buildr 1.3.3, change to your liking
# Version number for this release
VERSION_NUMBER = '0.1'
# Group identifier for your projects
GROUP = "jambda"
COPYRIGHT = "(c) 2008-2009 Johan Kullbom, Daniel Brolund, Joakim Ohlrogge"

# Specify Maven 2.0 remote repositories here, like this:
repositories.remote << "http://www.ibiblio.org/maven2/"
repositories.remote << "http://www.agical.com/maven2/"

BUMBLEBEE  = ['com.agical.bumblebee:bumblebee-core:jar:1.1.0']

desc "The jambda project"
define "jambda" do
  repositories.release_to = "file:///tmp/jambda_upload_repo"

  project.version = VERSION_NUMBER
  project.group = GROUP
  manifest["Implementation-Vendor"] = COPYRIGHT

  define "com.agical.jambda" do
    compile.with # Add classpath dependencies
    package(:jar)
    package :sources
    package :javadoc
  end

  desc "jambda scala bridge"
  define "com.agical.jambda.scala" do
    manifest["Implementation-Vendor"] = COPYRIGHT
    compile.with project('com.agical.jambda')
    package :jar
    package :sources
  end

  desc "jambda documentation"
  define "com.agical.jambda.demo" do
    manifest["Implementation-Vendor"] = COPYRIGHT
    compile.with project('com.agical.jambda')
    test.with BUMBLEBEE
    test.include 'com.agical.jambda.demo.TestSuite'
    package :jar
  	package(:zip, :id => 'jambda').
    include(compile.classpath, :path=>"lib").
    include(_("target/site/*"), :path=>"doc").
    include(_("src"), :path=>"com.agical.jambda.demo").
    include(_("LICENSE.txt"), :path=>".").

    include(_("../com.agical.jambda/target/javadoc/*"), :path=>"javadoc").
    include(_("../com.agical.jambda/src"), :path=>"com.agical.jambda").

    include(_("../com.agical.jambda.scala/target/*.jar"), :path=>"lib").
    include(_("../com.agical.jambda.scala/src"), :path=>"com.agical.jambda.scala").
    include(_("../buildfile"), :path=>".")
  end

end
