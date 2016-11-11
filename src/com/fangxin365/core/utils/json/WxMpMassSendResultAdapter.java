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

import com.fangxin365.wechat.entity.result.WxMpMassSendResult;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

public class WxMpMassSendResultAdapter implements JsonDeserializer<WxMpMassSendResult> {

	public WxMpMassSendResult deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
		WxMpMassSendResult sendResult = new WxMpMassSendResult();
		JsonObject sendResultJsonObject = json.getAsJsonObject();

		if (sendResultJsonObject.get("errcode") != null && !sendResultJsonObject.get("errcode").isJsonNull()) {
			sendResult.setErrorCode(GsonHelper.getAsString(sendResultJsonObject.get("errcode")));
		}
		if (sendResultJsonObject.get("errmsg") != null && !sendResultJsonObject.get("errmsg").isJsonNull()) {
			sendResult.setErrorMsg(GsonHelper.getAsString(sendResultJsonObject.get("errmsg")));
		}
		if (sendResultJsonObject.get("msg_id") != null && !sendResultJsonObject.get("msg_id").isJsonNull()) {
			sendResult.setMsgId(GsonHelper.getAsString(sendResultJsonObject.get("msg_id")));
		}
		return sendResult;
	}

}
