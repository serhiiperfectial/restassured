
package jsonSchemas;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.testcontainers.shaded.com.fasterxml.jackson.annotation.*;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "token",
    "channel",
    "text",
    "as_user",
    "attachments",
    "blocks",
    "icon_emoji",
    "icon_url",
    "link_names",
    "mrkdwn",
    "parse",
    "reply_broadcast",
    "thread_ts",
    "unfurl_links",
    "unfurl_media",
    "username"
})
public class PostMessage {

    @JsonProperty("token")
    private String token;
    @JsonProperty("channel")
    private String channel;
    @JsonProperty("text")
    private String text;
    @JsonProperty("as_user")
    private Boolean asUser;
    @JsonProperty("attachments")
    private List<Attachment> attachments = null;
    @JsonProperty("blocks")
    private List<Block> blocks = null;
    @JsonProperty("icon_emoji")
    private String iconEmoji;
    @JsonProperty("icon_url")
    private String iconUrl;
    @JsonProperty("link_names")
    private String linkNames;
    @JsonProperty("mrkdwn")
    private Boolean mrkdwn;
    @JsonProperty("parse")
    private String parse;
    @JsonProperty("reply_broadcast")
    private String replyBroadcast;
    @JsonProperty("thread_ts")
    private String threadTs;
    @JsonProperty("unfurl_links")
    private String unfurlLinks;
    @JsonProperty("unfurl_media")
    private String unfurlMedia;
    @JsonProperty("username")
    private String username;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("token")
    public String getToken() {
        return token;
    }

    @JsonProperty("token")
    public void setToken(String token) {
        this.token = token;
    }

    @JsonProperty("channel")
    public String getChannel() {
        return channel;
    }

    @JsonProperty("channel")
    public void setChannel(String channel) {
        this.channel = channel;
    }

    @JsonProperty("text")
    public String getText() {
        return text;
    }

    @JsonProperty("text")
    public void setText(String text) {
        this.text = text;
    }

    @JsonProperty("as_user")
    public Boolean getAsUser() {
        return asUser;
    }

    @JsonProperty("as_user")
    public void setAsUser(Boolean asUser) {
        this.asUser = asUser;
    }

    @JsonProperty("attachments")
    public List<Attachment> getAttachments() {
        return attachments;
    }

    @JsonProperty("attachments")
    public void setAttachments(List<Attachment> attachments) {
        this.attachments = attachments;
    }

    @JsonProperty("blocks")
    public List<Block> getBlocks() {
        return blocks;
    }

    @JsonProperty("blocks")
    public void setBlocks(List<Block> blocks) {
        this.blocks = blocks;
    }

    @JsonProperty("icon_emoji")
    public String getIconEmoji() {
        return iconEmoji;
    }

    @JsonProperty("icon_emoji")
    public void setIconEmoji(String iconEmoji) {
        this.iconEmoji = iconEmoji;
    }

    @JsonProperty("icon_url")
    public String getIconUrl() {
        return iconUrl;
    }

    @JsonProperty("icon_url")
    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }

    @JsonProperty("link_names")
    public String getLinkNames() {
        return linkNames;
    }

    @JsonProperty("link_names")
    public void setLinkNames(String linkNames) {
        this.linkNames = linkNames;
    }

    @JsonProperty("mrkdwn")
    public Boolean getMrkdwn() {
        return mrkdwn;
    }

    @JsonProperty("mrkdwn")
    public void setMrkdwn(Boolean mrkdwn) {
        this.mrkdwn = mrkdwn;
    }

    @JsonProperty("parse")
    public String getParse() {
        return parse;
    }

    @JsonProperty("parse")
    public void setParse(String parse) {
        this.parse = parse;
    }

    @JsonProperty("reply_broadcast")
    public String getReplyBroadcast() {
        return replyBroadcast;
    }

    @JsonProperty("reply_broadcast")
    public void setReplyBroadcast(String replyBroadcast) {
        this.replyBroadcast = replyBroadcast;
    }

    @JsonProperty("thread_ts")
    public String getThreadTs() {
        return threadTs;
    }

    @JsonProperty("thread_ts")
    public void setThreadTs(String threadTs) {
        this.threadTs = threadTs;
    }

    @JsonProperty("unfurl_links")
    public String getUnfurlLinks() {
        return unfurlLinks;
    }

    @JsonProperty("unfurl_links")
    public void setUnfurlLinks(String unfurlLinks) {
        this.unfurlLinks = unfurlLinks;
    }

    @JsonProperty("unfurl_media")
    public String getUnfurlMedia() {
        return unfurlMedia;
    }

    @JsonProperty("unfurl_media")
    public void setUnfurlMedia(String unfurlMedia) {
        this.unfurlMedia = unfurlMedia;
    }

    @JsonProperty("username")
    public String getUsername() {
        return username;
    }

    @JsonProperty("username")
    public void setUsername(String username) {
        this.username = username;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
