package cn.temobi.complex.interceptor;

import java.io.IOException;

import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializerProvider;
import org.codehaus.jackson.map.ser.StdSerializerProvider;
       
    public class ObjectMappingCustomer extends ObjectMapper  
    {  
       
        public ObjectMappingCustomer()  
        {  
            super();  
            // 空值处理为空串  
            StdSerializerProvider sp = (StdSerializerProvider) this.getSerializerProvider();
            sp.setNullValueSerializer(new JsonSerializer<Object>(){
 
                @Override
                public void serialize(Object value, JsonGenerator jg,
                        SerializerProvider sp) throws IOException,
                        JsonProcessingException {
                	String name = jg.getOutputContext().getCurrentName().toLowerCase();
                	if(name.indexOf("obj") == -1 && name.indexOf("list") == -1)
                	{
                		jg.writeString(""); 
                	}else{
                		jg.writeNull();
                	}
                }
                 
            });
       
        }  
    }



