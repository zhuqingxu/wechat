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
import java.text.ParseException;
import java.text.SimpleDateFormat;

import com.fangxin365.wechat.entity.result.WxMpUserCumulate;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

public class WxMpUserCumulateGsonAdapter implements JsonDeserializer<WxMpUserCumulate> {

	private static final SimpleDateFormat SIMPLE_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");

	public WxMpUserCumulate deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
		WxMpUserCumulate cumulate = new WxMpUserCumulate();
		JsonObject summaryJsonObject = json.getAsJsonObject();

		try {
			String refDate = GsonHelper.getString(summaryJsonObject, "ref_date");
			if (refDate != null) {
				cumulate.setRefDate(SIMPLE_DATE_FORMAT.parse(refDate));
			}
			cumulate.setCumulateUser(GsonHelper.getInteger(summaryJsonObject, "cumulate_user"));
		} catch (ParseException e) {
			throw new JsonParseException(e);
		}
		return cumulate;
	}

}