package org.noear.fairy.encoder;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.parser.ParserConfig;
import com.alibaba.fastjson.serializer.SerializerFeature;
import org.noear.fairy.Enctype;
import org.noear.fairy.IEncoder;


public class FastjsonEncoder implements IEncoder {
    static {
        ParserConfig.getGlobalInstance().setAutoTypeSupport(true);
    }

    public static final FastjsonEncoder instance = new FastjsonEncoder();


    @Override
    public Enctype enctype() {
        return Enctype.application_json;
    }

    @Override
    public Object encode(Object obj) {
        return JSON.toJSONString(obj,
                SerializerFeature.BrowserCompatible,
                SerializerFeature.DisableCircularReferenceDetect);
    }
}
