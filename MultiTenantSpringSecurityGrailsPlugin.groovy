/* Copyright 2010 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
import org.grails.multitenant.springsecurity.SpringSecurityCurrentTenant
import org.codehaus.groovy.grails.commons.ConfigurationHolder

/**
 *
 * @author <a href='mailto:limcheekin@vobject.com'>Lim Chee Kin</a>
 * @author <a href='mailto:steve@ronderos.com'>Steve Ronderos</a>
 *
 * @since 0.1
 */
class MultiTenantSpringSecurityGrailsPlugin {
    // the plugin version
    def version = "0.2.1"
    // the version or versions of Grails the plugin is designed for
    def grailsVersion = "1.3.5 > *"
    // the other plugins this plugin depends on
    def dependsOn = [springSecurityCore:"1.0.0 > *", multiTenantCore:"1.0.0 > *"]
    // resources that are excluded from plugin packaging
    def pluginExcludes = [
            "grails-app/views/error.gsp"
    ]

    def author = "Lim Chee Kin"
    def authorEmail = "limcheekin@vobject.com"
    def title = "Multi-Tenant Spring Security Integration"
    def description = '''\
Integrates the multi-tenant-core plugin (http://www.grails.org/plugin/multi-tenant-core) with the spring-security-core plugin (http://www.grails.org/plugin/spring-security-core), so that the current tenant can be determined from the authenticated principal.

Provides a custom CurrentTenant implementation that uses the security context to identify the current tenant.  This allows all users to login from 
one url instead of having to remember a special url to log in to.

This plugin's code is based on the code of multi-tenant-acegi plugin (http://www.grails.org/plugin/multi-tenant-acegi). All configurations are same with multi-tenant-acegi plugin except the tenant resolver property in Config.groovy.
Your tenant resolver property should look like this:
{code}
tenant {
    resolver.type = "springSecurity"
}
{code}
'''

    // URL to the plugin's documentation
    def documentation = "http://grails.org/plugin/multi-tenant-spring-security"

    def doWithWebDescriptor = { xml ->
        // TODO Implement additions to web.xml (optional), this event occurs before 
    }

    def doWithSpring = {
        if (ConfigurationHolder.config.tenant.resolver.type == "springSecurity") {
            currentTenant(SpringSecurityCurrentTenant) {
                eventBroker = ref("eventBroker")
            }
        }
    }

    def doWithDynamicMethods = { ctx ->
        // TODO Implement registering dynamic methods to classes (optional)
    }

    def doWithApplicationContext = { applicationContext ->
        // TODO Implement post initialization spring config (optional)
    }

    def onChange = { event ->
        // TODO Implement code that is executed when any artefact that this plugin is
        // watching is modified and reloaded. The event contains: event.source,
        // event.application, event.manager, event.ctx, and event.plugin.
    }

    def onConfigChange = { event ->
        // TODO Implement code that is executed when the project configuration changes.
        // The event is the same as for 'onChange'.
    }
}
