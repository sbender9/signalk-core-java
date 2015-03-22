package nz.co.fortytwo.signalk.model.impl;

import static nz.co.fortytwo.signalk.util.SignalKConstants.nav_position_latitude;
import static nz.co.fortytwo.signalk.util.SignalKConstants.vessels_dot_self_dot;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.IOException;

import mjson.Json;
import nz.co.fortytwo.signalk.handler.AISHandler;
import nz.co.fortytwo.signalk.handler.FullToDeltaConverter;
import nz.co.fortytwo.signalk.handler.N2KHandler;
import nz.co.fortytwo.signalk.handler.NMEAHandler;
import nz.co.fortytwo.signalk.model.SignalKModel;
import nz.co.fortytwo.signalk.util.JsonSerializer;

import org.apache.log4j.Logger;
import org.junit.Test;

public class SignalKExamplesGenerator {
	private static Logger logger = Logger.getLogger(SignalKExamplesGenerator.class);
	private JsonSerializer ser = new JsonSerializer();
	private FullToDeltaConverter conv = new FullToDeltaConverter();
	
	//NMEA
	@Test
	public void shouldHandleGPRMC() throws IOException{
		 String nmea1 = "$GPRMC,144629.20,A,5156.91111,N,00434.80385,E,0.295,,011113,,,A*78";
		 logger.debug("Converting "+nmea1);
		// String nmea2 = "$GPRMC,144629.30,A,5156.91115,N,00434.80383,E,1.689,,011113,,,A*73";
		// String nmea3 = "$GPRMC,144629.50,A,5156.91127,N,00434.80383,E,1.226,,011113,,,A*75";
		 NMEAHandler processor = new NMEAHandler();
		
		 SignalKModel model = processor.handle(nmea1);
		 print(model);
		 //assertEquals(51.9485185d,(double)model.get(vessels_dot_self_dot +nav_position_latitude),0.00001);
		 //logger.debug("Lat :"+model.get(vessels_dot_self_dot +nav_position_latitude));
	}
	
	@Test
	public void shouldHandleNMEA0183() throws IOException{
		String[] sentences = new String[]{"$IIVHW,,T,,M,06.11,N,11.31,K*51",
				//"$IIVPW,4.71,N,,*03",
				//"$IIVTG,224.44,T,224.44,M,5.81,N,,,D*68",
				"$IIMWV,338,R,13.41,N,A*2C",
				//"$IIVWT,039,L,08.10,N,04.17,M,,*2B",
				"$IIDBT,034.25,f,010.44,M,005.64,F*27",
				"$GPGLL,6005.071,N,02332.346,E,095559,A,D*43"};
				//"$IIMWD,,,,,08.16,N,04.20,M*54"};

		NMEAHandler processor = new NMEAHandler();
		for(String nmea: sentences){
			logger.debug("Converting "+nmea);
		 SignalKModel model = processor.handle(nmea);
		 print(model);
		}
		 //assertEquals(51.9485185d,(double)model.get(vessels_dot_self_dot +nav_position_latitude),0.00001);
		 //logger.debug("Lat :"+model.get(vessels_dot_self_dot +nav_position_latitude));
	}
	
	
//AIS
	@Test
	//@Ignore
	public void shouldParseSingleAISMessage() throws Exception{
		String msg = "!AIVDM,1,1,,B,15MwkRUOidG?GElEa<iQk1JV06Jd,0*6D";
		AISHandler processor = new AISHandler();
		SignalKModel model =  processor.handle(msg);
		assertNotNull(model);
		
		logger.debug("Converting "+msg);
		print(model);
	}
	
	@Test
	//@Ignore
	public void shouldParseTwoAISMessages() throws Exception{

		String msg1 = "!AIVDM,1,1,,A,15MvJw5P0NG?Us6EaDVTTOvR06Jd,0*22";
		logger.debug("Converting "+msg1);
		AISHandler processor = new AISHandler();
		SignalKModel model = processor.handle(msg1);

		String msg = "!AIVDM,1,1,,B,15Mtu:0000o@05tE`?Ctn@6T06Jd,0*40";
		logger.debug("Converting "+msg);
		model = processor.handle(msg);
		print(model);
	}
	
	//N2K
	@Test
	public void shouldConvertN2K() throws IOException{
		String json = "{\"timestamp\":\"2014-08-15-18:00:10.005\",\"prio\":\"2\",\"src\":\"160\",\"dst\":\"255\",\"pgn\":\"129026\",\"description\":\"COG & SOG, Rapid Update\",\"fields\":{\"COG_Reference\":\"True\",\"COG\":\"206.1\",\"SOG\":\"3.65\"}}";
		logger.debug("Converting "+Json.read(json));
		N2KHandler handler = new N2KHandler();
		SignalKModel model = handler.handle(json);
		print(model);
	}
	
	
	private void print(SignalKModel model) throws IOException {
		
		 logger.debug("Signal K key/value tree: "+model);
		 Json full = ser.writeJson(model);  
		 logger.debug("Signal K full json format: "+ full);
		 logger.debug("Signal K delta json format: "+ conv.handle(full));
		 logger.debug("\n");
		
	}
}
