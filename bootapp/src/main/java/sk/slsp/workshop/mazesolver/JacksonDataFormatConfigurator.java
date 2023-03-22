package sk.slsp.workshop.mazesolver;

import org.camunda.spin.impl.json.jackson.format.JacksonJsonDataFormat;
import org.camunda.spin.spi.DataFormatConfigurator;
import org.springframework.stereotype.Service;
import spinjar.com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class JacksonDataFormatConfigurator implements DataFormatConfigurator<JacksonJsonDataFormat> {

	@Override
	public Class<JacksonJsonDataFormat> getDataFormatClass() {
		return JacksonJsonDataFormat.class;
	}

	@Override
	public void configure(JacksonJsonDataFormat jacksonJsonDataFormat) {

		jacksonJsonDataFormat.setObjectMapper(
				new ObjectMapper()
		);
	}
}
