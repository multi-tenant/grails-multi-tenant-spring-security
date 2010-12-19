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
package org.grails.multitenant.springsecurity

import grails.plugin.multitenant.core.CurrentTenant
import org.springframework.security.core.context.SecurityContextHolder
import com.infusion.util.event.EventBroker
import grails.plugin.multitenant.core.event.TenantChangedEvent
import org.codehaus.groovy.grails.commons.ApplicationHolder as AH

/**
 * @author Eric Martineau
 * @author <a href='mailto:limcheekin@vobject.com'>Lim Chee Kin</a>
 *
 * @since 0.1
 */
public class SpringSecurityCurrentTenant implements CurrentTenant {
	
	EventBroker eventBroker
	
	private ThreadLocal<Integer> tmpTenant = new ThreadLocal<Integer>()
	private List<Integer> loaded = new ArrayList<Integer>();
	
	public Integer get() {
		Integer rtn
		if (tmpTenant.get() != null) {
			rtn = tmpTenant.get()
		} else {
			rtn = getTenantIdFromSpringSecurity()
		}
		return rtn
	}
	
	private Integer getTenantIdFromSpringSecurity() {
		Object principal = SecurityContextHolder.getContext().getAuthentication()?.getPrincipal()
		
		if (principal?.respondsTo('id')) {
			def userClass = AH.application.getDomainClass(AH.application.config.grails.plugins.springsecurity.userLookup.userDomainClassName) 
			def userInstance = userClass.clazz.get(principal.id)   
			Integer tid = userInstance?.userTenantId
			if (tid) {
				if (!loaded.contains(tid)) {
					loaded.add(tid)
					eventBroker?.publish("newTenant", new TenantChangedEvent(0, tid))
				}
			}
			return tid
		} else {
			return 0;
		}
	}
	
	public void set(Integer tenantId) {
		Integer tenantIdFromSpringSecurity = getTenantIdFromSpringSecurity();
		if (tenantIdFromSpringSecurity == tenantId) {
			tmpTenant.set(null)
		} else {
			tmpTenant.set(tenantId)
		}
	}
	
	public void resetLoadedCache ( ) {
		loaded.clear()
	}
}
