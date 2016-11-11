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

import com.fangxin365.wechat.entity.result.WxMpMaterialUploadResult;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

public class WxMpMaterialUploadResultAdapter implements JsonDeserializer<WxMpMaterialUploadResult> {

	public WxMpMaterialUploadResult deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
		WxMpMaterialUploadResult uploadResult = new WxMpMaterialUploadResult();
		JsonObject uploadResultJsonObject = json.getAsJsonObject();

		if (uploadResultJsonObject.get("url") != null && !uploadResultJsonObject.get("url").isJsonNull()) {
			uploadResult.setUrl(GsonHelper.getAsString(uploadResultJsonObject.get("url")));
		}
		if (uploadResultJsonObject.get("media_id") != null && !uploadResultJsonObject.get("media_id").isJsonNull()) {
			uploadResult.setMediaId(GsonHelper.getAsString(uploadResultJsonObject.get("media_id")));
		}
		return uploadResult;
	}

}
