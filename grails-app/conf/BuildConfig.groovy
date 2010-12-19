/**
 * This module defines the build dependencies for the plugin to support such items as the OSCache functionality, etc.
 */
grails.project.dependency.resolution =
  {
    // inherit Grails' default dependencies
    inherits "global"
    
    log "warn" // log level of Ivy resolver, either 'error', 'warn', 'info', 'debug' or 'verbose'
    repositories
    {
      grailsPlugins()
      grailsHome()
      grailsCentral()
      mavenCentral()
      mavenRepo "http://download.java.net/maven/2/"
      mavenRepo "http://repository.jboss.com/maven2/"
    }
    dependencies
    {
      // specify dependencies here under either 'build', 'compile', 'runtime', 'test' or 'provided' scopes eg.
    }
  }
