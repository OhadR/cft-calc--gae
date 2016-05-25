/*
 * Copyright 2014 the original author or authors.
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
package com.ohadr.cbenchmarkr.signin;

import java.util.Date;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.web.SignInAdapter;
import org.springframework.social.facebook.api.AgeRange;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.facebook.api.User;
import org.springframework.social.facebook.api.UserOperations;
import org.springframework.web.context.request.NativeWebRequest;

import com.ohadr.cbenchmarkr.GAERepositoryImpl;

public class ImplicitSignInAdapter implements SignInAdapter {

	private static Logger log = Logger.getLogger(ImplicitSignInAdapter.class);

	private final RequestCache requestCache;

	@Autowired
	private GAERepositoryImpl repository;  //GAEAccountRepositoryImpl  implements UserDetailsManager

	@Inject
	public ImplicitSignInAdapter(RequestCache requestCache) {
		this.requestCache = requestCache;
	}
	
	@Override
	public String signIn(String localUserId, Connection<?> connection, NativeWebRequest request) {

		getUserDataAndPersist(connection);
		
		String providerUserId = connection.getKey().getProviderUserId();
		SignInUtils.signin(providerUserId);
//		return extractOriginalUrl(request);
		return "/#/facebookLogin";
	}

	private String extractOriginalUrl(NativeWebRequest request) {
		HttpServletRequest nativeReq = request.getNativeRequest(HttpServletRequest.class);
		HttpServletResponse nativeRes = request.getNativeResponse(HttpServletResponse.class);
		SavedRequest saved = requestCache.getRequest(nativeReq, nativeRes);
		if (saved == null) {
			return null;
		}
		requestCache.removeRequest(nativeReq, nativeRes);
		removeAutheticationAttributes(nativeReq.getSession(false));
		return saved.getRedirectUrl();
	}
		 
	private void getUserDataAndPersist(Connection<?> connection)
	{
		Facebook facebookApi = (Facebook)connection.getApi();
		UserOperations facebookUserOperations = facebookApi.userOperations();

		User userProfile = facebookUserOperations.getUserProfile();

		String gender = userProfile.getGender();
        log.info( "creating traineeId: " + userProfile.getId() + ", isMale? " + gender  );

		repository.createBenchmarkrAccount(userProfile.getId(),
				userProfile.getFirstName(),
				userProfile.getLastName(),
				userProfile.getGender().equalsIgnoreCase("male") );
	}
	
	private void removeAutheticationAttributes(HttpSession session) {
		if (session == null) {
			return;
		}
		session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
	}

}
