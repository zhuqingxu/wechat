/*
 * KINGSTAR MEDIA SOLUTIONS Co.,LTD. Copyright c 2005-2013. All rights reserved.
 *
 * This source code is the property of KINGSTAR MEDIA SOLUTIONS LTD. It is intended
 * only for the use of KINGSTAR MEDIA application development. Reengineering, reproduction
 * arose from modification of the original source, or other redistribution of this source
 * is not permitted without written permission of the KINGSTAR MEDIA SOLUTIONS LTD.
 */
package com.fangxin365.core.utils.json;

import java.lang.reflect.Type;

import com.fangxin365.wechat.entity.WxAccessToken;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

public class WxAccessTokenAdapter implements JsonDeserializer<WxAccessToken> {

	public WxAccessToken deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
		WxAccessToken accessToken = new WxAccessToken();
		JsonObject accessTokenJsonObject = json.getAsJsonObject();

		if (accessTokenJsonObject.get("access_token") != null && !accessTokenJsonObject.get("access_token").isJsonNull()) {
			accessToken.setAccessToken(GsonHelper.getAsString(accessTokenJsonObject.get("access_token")));
		}
		if (accessTokenJsonObject.get("expires_in") != null && !accessTokenJsonObject.get("expires_in").isJsonNull()) {
			accessToken.setExpiresIn(GsonHelper.getAsPrimitiveInt(accessTokenJsonObject.get("expires_in")));
		}
		return accessToken;
	}

}
