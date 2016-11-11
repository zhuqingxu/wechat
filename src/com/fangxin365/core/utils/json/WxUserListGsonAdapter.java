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

import com.fangxin365.wechat.entity.result.WxMpUserList;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

public class WxUserListGsonAdapter implements JsonDeserializer<WxMpUserList> {

	public WxMpUserList deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
		JsonObject o = json.getAsJsonObject();
		WxMpUserList wxMpUserList = new WxMpUserList();
		wxMpUserList.setTotal(GsonHelper.getInteger(o, "total"));
		wxMpUserList.setCount(GsonHelper.getInteger(o, "count"));
		wxMpUserList.setNextOpenId(GsonHelper.getString(o, "next_openid"));
		if (o.get("data") != null && !o.get("data").isJsonNull() && !o.get("data").getAsJsonObject().get("openid").isJsonNull()) {
			JsonArray data = o.get("data").getAsJsonObject().get("openid").getAsJsonArray();
			for (int i = 0; i < data.size(); i++) {
				wxMpUserList.getOpenIds().add(GsonHelper.getAsString(data.get(i)));
			}
		}
		return wxMpUserList;
	}

}