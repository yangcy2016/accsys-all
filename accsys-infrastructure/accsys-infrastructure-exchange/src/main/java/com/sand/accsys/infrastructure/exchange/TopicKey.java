package com.sand.accsys.infrastructure.exchange;

/**
 * @author : huanghy
 * @create : 2016/3/17 0017 上午 9:18
 * @since : ${VERSION}
 */
public class TopicKey {
    private final String topicName;
    private final ChannelType channelType;

    public TopicKey(String topicName, ChannelType channelType) {
        this.topicName = topicName;
        this.channelType = channelType;
    }

    public String getTopicName() {
        return topicName;
    }

    public ChannelType getChannelType() {
        return channelType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TopicKey)) return false;

        TopicKey topicKey = (TopicKey) o;

        if (topicName != null ? !topicName.equals(topicKey.topicName) : topicKey.topicName != null) return false;
        return channelType == topicKey.channelType;

    }

    @Override
    public int hashCode() {
        int result = topicName != null ? topicName.hashCode() : 0;
        result = 31 * result + (channelType != null ? channelType.hashCode() : 0);
        return result;
    }

    @Override
    public String toString(){
        return "[topic:"+topicName+",type:"+channelType+"]";
    }
}
