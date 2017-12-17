package spring.cloud.client.uitils;

import com.google.common.base.Strings;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.Optional;

/**
 * Created by Harry on 2017/5/11.
 */
public class XmlParser {

    public static Optional<String> javaToXml(Object object) {
        if ( null == object ) return Optional.empty();
        try {
            JAXBContext context = JAXBContext.newInstance( object.getClass() );
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
//            marshaller.setProperty(Marshaller.JAXB_ENCODING, "utf-8");

            StringWriter writer = new StringWriter();
            marshaller.marshal(object, writer);
            return Optional.ofNullable( writer.toString() );
        } catch (JAXBException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    public static <T extends Object> Optional<T> xmlToJava(String xml, Class<T> clazz) {
        if (Strings.isNullOrEmpty(xml) || null == clazz){
            return Optional.empty();
        }
        try {
            JAXBContext context = JAXBContext.newInstance( clazz );
            Unmarshaller unmarshaller = context.createUnmarshaller();
//            unmarshaller.setProperty(Marshaller.JAXB_ENCODING, "utf-8");
            T obj = (T)unmarshaller.unmarshal(new StringReader(xml));
            return Optional.ofNullable( obj );
        } catch (JAXBException e) {
            e.printStackTrace();
        }

        return Optional.empty();
    }
}
