package com.fangxin365.core.utils.json;

import com.fangxin365.wechat.entity.WxMpCustomMessage;
import com.fangxin365.wechat.entity.WxMpGroup;
import com.fangxin365.wechat.entity.WxMpMassGroupMessage;
import com.fangxin365.wechat.entity.WxMpMassNews;
import com.fangxin365.wechat.entity.WxMpMassOpenIdsMessage;
import com.fangxin365.wechat.entity.WxMpMassVideo;
import com.fangxin365.wechat.entity.WxMpMaterialArticleUpdate;
import com.fangxin365.wechat.entity.WxMpMaterialNews;
import com.fangxin365.wechat.entity.WxMpTemplateMessage;
import com.fangxin365.wechat.entity.result.WxMpMassSendResult;
import com.fangxin365.wechat.entity.result.WxMpMassUploadResult;
import com.fangxin365.wechat.entity.result.WxMpMaterialCountResult;
import com.fangxin365.wechat.entity.result.WxMpMaterialFileBatchGetResult;
import com.fangxin365.wechat.entity.result.WxMpMaterialNewsBatchGetResult;
import com.fangxin365.wechat.entity.result.WxMpMaterialUploadResult;
import com.fangxin365.wechat.entity.result.WxMpMaterialVideoInfoResult;
import com.fangxin365.wechat.entity.result.WxMpOAuth2AccessToken;
import com.fangxin365.wechat.entity.result.WxMpQrCodeTicket;
import com.fangxin365.wechat.entity.result.WxMpSemanticQueryResult;
import com.fangxin365.wechat.entity.result.WxMpUser;
import com.fangxin365.wechat.entity.result.WxMpUserCumulate;
import com.fangxin365.wechat.entity.result.WxMpUserList;
import com.fangxin365.wechat.entity.result.WxMpUserSummary;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class WxMpGsonBuilder {

	public static final GsonBuilder INSTANCE = new GsonBuilder();

	static {
		INSTANCE.disableHtmlEscaping();
		INSTANCE.registerTypeAdapter(WxMpCustomMessage.class, new WxMpCustomMessageGsonAdapter());
		INSTANCE.registerTypeAdapter(WxMpMassNews.class, new WxMpMassNewsGsonAdapter());
		INSTANCE.registerTypeAdapter(WxMpMassGroupMessage.class, new WxMpMassGroupMessageGsonAdapter());
		INSTANCE.registerTypeAdapter(WxMpMassOpenIdsMessage.class, new WxMpMassOpenIdsMessageGsonAdapter());
		INSTANCE.registerTypeAdapter(WxMpGroup.class, new WxMpGroupGsonAdapter());
		INSTANCE.registerTypeAdapter(WxMpUser.class, new WxMpUserGsonAdapter());
		INSTANCE.registerTypeAdapter(WxMpUserList.class, new WxUserListGsonAdapter());
		INSTANCE.registerTypeAdapter(WxMpMassVideo.class, new WxMpMassVideoAdapter());
		INSTANCE.registerTypeAdapter(WxMpMassSendResult.class, new WxMpMassSendResultAdapter());
		INSTANCE.registerTypeAdapter(WxMpMassUploadResult.class, new WxMpMassUploadResultAdapter());
		INSTANCE.registerTypeAdapter(WxMpQrCodeTicket.class, new WxQrCodeTicketAdapter());
		INSTANCE.registerTypeAdapter(WxMpTemplateMessage.class, new WxMpTemplateMessageGsonAdapter());
		INSTANCE.registerTypeAdapter(WxMpSemanticQueryResult.class, new WxMpSemanticQueryResultAdapter());
		INSTANCE.registerTypeAdapter(WxMpOAuth2AccessToken.class, new WxMpOAuth2AccessTokenAdapter());
		INSTANCE.registerTypeAdapter(WxMpUserSummary.class, new WxMpUserSummaryGsonAdapter());
		INSTANCE.registerTypeAdapter(WxMpUserCumulate.class, new WxMpUserCumulateGsonAdapter());
		INSTANCE.registerTypeAdapter(WxMpMaterialUploadResult.class, new WxMpMaterialUploadResultAdapter());
		INSTANCE.registerTypeAdapter(WxMpMaterialVideoInfoResult.class, new WxMpMaterialVideoInfoResultAdapter());
		INSTANCE.registerTypeAdapter(WxMpMassNews.WxMpMassNewsArticle.class, new WxMpMassNewsArticleGsonAdapter());
		INSTANCE.registerTypeAdapter(WxMpMaterialArticleUpdate.class, new WxMpMaterialArticleUpdateGsonAdapter());
		INSTANCE.registerTypeAdapter(WxMpMaterialCountResult.class, new WxMpMaterialCountResultAdapter());
		INSTANCE.registerTypeAdapter(WxMpMaterialNews.class, new WxMpMaterialNewsGsonAdapter());
		INSTANCE.registerTypeAdapter(WxMpMaterialNews.WxMpMaterialNewsArticle.class, new WxMpMaterialNewsArticleGsonAdapter());
		INSTANCE.registerTypeAdapter(WxMpMaterialNewsBatchGetResult.class, new WxMpMaterialNewsBatchGetGsonAdapter());
		INSTANCE.registerTypeAdapter(WxMpMaterialNewsBatchGetResult.WxMaterialNewsBatchGetNewsItem.class, new WxMpMaterialNewsBatchGetGsonItemAdapter());
		INSTANCE.registerTypeAdapter(WxMpMaterialFileBatchGetResult.class, new WxMpMaterialFileBatchGetGsonAdapter());
		INSTANCE.registerTypeAdapter(WxMpMaterialFileBatchGetResult.WxMaterialFileBatchGetNewsItem.class, new WxMpMaterialFileBatchGetGsonItemAdapter());
	}

	public static Gson create() {
		return INSTANCE.create();
	}

}
