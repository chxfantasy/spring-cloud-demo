package spring.cloud.client.model.enumModel;

/**
 * Created by jiaguang
 */
public enum AppEnvType {
    DEV((byte)3, "dev"), DAILY((byte)0, "daily"), PRE((byte)1, "pre"), PROD((byte)2, "prod");

    private byte value;
    private String desc;

    private AppEnvType(byte value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    public byte getValue() {
        return value;
    }

    public String getDesc() {
        return desc;
    }

    public void setValue(byte value) {
        this.value = value;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
