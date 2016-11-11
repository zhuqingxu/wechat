package com.fangxin365.core.utils.json;

import java.lang.reflect.Type;

import com.fangxin365.wechat.entity.result.WxMpMaterialVideoInfoResult;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

public class WxMpMaterialVideoInfoResultAdapter implements JsonDeserializer<WxMpMaterialVideoInfoResult> {

	public WxMpMaterialVideoInfoResult deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
		WxMpMaterialVideoInfoResult uploadResult = new WxMpMaterialVideoInfoResult();
		JsonObject uploadResultJsonObject = json.getAsJsonObject();

		if (uploadResultJsonObject.get("title") != null && !uploadResultJsonObject.get("title").isJsonNull()) {
			uploadResult.setTitle(GsonHelper.getAsString(uploadResultJsonObject.get("title")));
		}
		if (uploadResultJsonObject.get("description") != null && !uploadResultJsonObject.get("description").isJsonNull()) {
			uploadResult.setDescription(GsonHelper.getAsString(uploadResultJsonObject.get("description")));
		}
		if (uploadResultJsonObject.get("down_url") != null && !uploadResultJsonObject.get("down_url").isJsonNull()) {
			uploadResult.setDownUrl(GsonHelper.getAsString(uploadResultJsonObject.get("down_url")));
		}
		return uploadResult;
	}

}
