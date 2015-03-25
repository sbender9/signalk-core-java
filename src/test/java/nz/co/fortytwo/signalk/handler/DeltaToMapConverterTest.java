/*
 *
 * Copyright (C) 2012-2014 R T Huitema. All Rights Reserved.
 * Web: www.42.co.nz
 * Email: robert@42.co.nz
 * Author: R T Huitema
 *
 * This file is provided AS IS with NO WARRANTY OF ANY KIND, INCLUDING THE
 * WARRANTY OF DESIGN, MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */
package nz.co.fortytwo.signalk.handler;

import static nz.co.fortytwo.signalk.util.JsonConstants.SELF;
import static nz.co.fortytwo.signalk.util.JsonConstants.SOURCE;
import static nz.co.fortytwo.signalk.util.JsonConstants.SOURCE;
import static nz.co.fortytwo.signalk.util.JsonConstants.VALUE;
import static nz.co.fortytwo.signalk.util.SignalKConstants.nav_courseOverGroundMagnetic;
import static nz.co.fortytwo.signalk.util.SignalKConstants.nav_courseOverGroundTrue;
import static nz.co.fortytwo.signalk.util.SignalKConstants.nav_speedOverGround;
import static nz.co.fortytwo.signalk.util.SignalKConstants.nav_speedThroughWater;
import static nz.co.fortytwo.signalk.util.SignalKConstants.vessels_dot_self_dot;
import static org.junit.Assert.*;
import mjson.Json;
import nz.co.fortytwo.signalk.model.SignalKModel;
import nz.co.fortytwo.signalk.model.impl.SignalKModelFactory;
import nz.co.fortytwo.signalk.model.impl.SignalKModelImpl;

import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class DeltaToMapConverterTest {

	String jsonDiff = "{\"context\": \"vessels."+SELF+".navigation\",\"updates\":[{\"source\": {\"device\" : \"/dev/actisense\",\"timestamp\":\"2014-08-15T16:00:00.081+00:00\",\"src\":\"115\",\"pgn\":\"128267\"},\"values\": [{ \"path\": \"courseOverGroundTrue\",\"value\": 172.9 },{ \"path\": \"speedOverGround\",\"value\": 3.85 }]}]}";
	String jsonDiff1 = "{\"context\": \"vessels."+SELF+"\",\"updates\":[{\"source\": {\"device\" : \"/dev/actisense\", \"timestamp\":\"2014-08-15T16:00:00.081+00:00\",\"src\":\"115\",\"pgn\":\"128267\"},\"values\": [{ \"path\": \"navigation.courseOverGroundTrue\",\"value\": 172.9 },{ \"path\": \"navigation.speedOverGround\",\"value\": 3.85 }]}]}";
	String jsonDiff2 = "{\"context\": \"vessels."+SELF+".navigation\",\"updates\":[{\"source\": {\"device\" : \"/dev/actisense\",\"timestamp\":\"2014-08-15T16:00:00.081+00:00\",\"src\":\"115\",\"pgn\":\"128267\"},\"values\": [{ \"path\": \"courseOverGroundTrue\",\"value\": 172.9 },{ \"path\": \"speedOverGround\",\"value\": 3.85 }]},{\"source\": {\"device\" : \"/dev/ttyUSB0\",\"timestamp\":\"2014-08-15T16:00:00.081+00:00\",\"src\":\"115\",\"pgn\":\"128267\"},\"values\": [{ \"path\": \"courseOverGroundMagnetic\",\"value\": 152.9 },{ \"path\": \"speedThroughWater\",\"value\": 2.85 }]}]}";
	String jsonDiff3 = "{\"updates\":[{\"values\":[{\"value\":172.9,\"path\":\"courseOverGroundTrue\"},{\"value\":3.85,\"path\":\"speedOverGround\"}],\"source\":{\"timestamp\":\"2014-08-15T16:00:00.081+00:00\",\"device\":\"/dev/actisense\",\"pgn\":\"128267\",\"src\":\"115\"}}],\"context\":\"vessels."+SELF+".navigation\"}";
	private static Logger logger = Logger.getLogger(SignalKModelImpl.class);
	@Before
	public void setUp() throws Exception {
		
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void shouldProcessDiff() {
		Json diff = Json.read(jsonDiff);
		DeltaToMapConverter processor = new DeltaToMapConverter();
		SignalKModel output = processor.handle(diff);
		logger.debug(output);
		assertEquals(172.9, (double)output.getValue(vessels_dot_self_dot+nav_courseOverGroundTrue),001);
		assertEquals("2014-08-15T16:00:00.081+00:00", output.get(vessels_dot_self_dot+nav_courseOverGroundTrue+"."+SOURCE+".timestamp"));
		assertEquals("/dev/actisense", output.get(vessels_dot_self_dot+nav_courseOverGroundTrue+"."+SOURCE+".device"));
		
		assertEquals(3.85, (double)output.get(vessels_dot_self_dot+nav_speedOverGround+"."+VALUE),001);
		assertEquals("2014-08-15T16:00:00.081+00:00", output.get(vessels_dot_self_dot+nav_speedOverGround+"."+SOURCE+".timestamp"));
		assertEquals("/dev/actisense", output.get(vessels_dot_self_dot+nav_speedOverGround+"."+SOURCE+".device"));
	}
	@Test
	public void shouldProcessDiff3() {
		Json diff = Json.read(jsonDiff3);
		DeltaToMapConverter processor = new DeltaToMapConverter();
		SignalKModel output = processor.handle(diff);
		logger.debug(output);
		assertEquals(172.9,(double) output.get(vessels_dot_self_dot+nav_courseOverGroundTrue+"."+VALUE),001);
		assertEquals("2014-08-15T16:00:00.081+00:00", output.get(vessels_dot_self_dot+nav_courseOverGroundTrue+"."+SOURCE+".timestamp"));
		assertEquals("/dev/actisense", output.get(vessels_dot_self_dot+nav_courseOverGroundTrue+"."+SOURCE+".device"));
		
		assertEquals(3.85, (double)output.get(vessels_dot_self_dot+nav_speedOverGround+"."+VALUE),001);
		assertEquals("2014-08-15T16:00:00.081+00:00", output.get(vessels_dot_self_dot+nav_speedOverGround+"."+SOURCE+".timestamp"));
		assertEquals("/dev/actisense", output.get(vessels_dot_self_dot+nav_speedOverGround+"."+SOURCE+".device"));
	}

	@Test
	public void shouldIgnoreSignalKJson() {
		Json diff = Json.read("{\"vessels\":{\""+SELF+"\":{\"navigation\":{\"courseOverGroundTrue\": {\"value\":11.9600000381},\"courseOverGroundMagnetic\": {\"value\":93.0000000000},\"headingMagnetic\": {\"value\":0.0000000000},\"magneticVariation\": {\"value\":0.0000000000},\"headingTrue\": {\"value\":0.0000000000},\"pitch\": {\"value\":0.0000000000},\"rateOfTurn\": {\"value\":0.0000000000},\"roll\": {\"value\":0.0000000000},\"speedOverGround\": {\"value\":0.0399999980},\"speedThroughWater\": {\"value\":0.0000000000},\"state\": {\"value\":\"Not defined (example)\"},\"anchor\":{\"alarmRadius\": {\"value\":0.0000000000},\"maxRadius\": {\"value\":0.0000000000},\"position\":{\"latitude\": {\"value\":-41.2936935424},\"longitude\": {\"value\":173.2470855712},\"altitude\": {\"value\":0.0000000000}}},\"position\":{\"latitude\": {\"value\":-41.2936935424},\"longitude\": {\"value\":173.2470855712},\"altitude\": {\"value\":0.0000000000}}},\"alarm\":{\"anchorAlarmMethod\": {\"value\":\"sound\"},\"anchorAlarmState\": {\"value\":\"disabled\"},\"autopilotAlarmMethod\": {\"value\":\"sound\"},\"autopilotAlarmState\": {\"value\":\"disabled\"},\"engineAlarmMethod\": {\"value\":\"sound\"},\"engineAlarmState\": {\"value\":\"disabled\"},\"fireAlarmMethod\": {\"value\":\"sound\"},\"fireAlarmState\": {\"value\":\"disabled\"},\"gasAlarmMethod\": {\"value\":\"sound\"},\"gasAlarmState\": {\"value\":\"disabled\"},\"gpsAlarmMethod\": {\"value\":\"sound\"},\"gpsAlarmState\": {\"value\":\"disabled\"},\"maydayAlarmMethod\": {\"value\":\"sound\"},\"maydayAlarmState\": {\"value\":\"disabled\"},\"panpanAlarmMethod\": {\"value\":\"sound\"},\"panpanAlarmState\": {\"value\":\"disabled\"},\"powerAlarmMethod\": {\"value\":\"sound\"},\"powerAlarmState\": {\"value\":\"disabled\"},\"silentInterval\": {\"value\":300},\"windAlarmMethod\": {\"value\":\"sound\"},\"windAlarmState\": {\"value\":\"disabled\"},\"genericAlarmMethod\": {\"value\":\"sound\"},\"genericAlarmState\": {\"value\":\"disabled\"},\"radarAlarmMethod\": {\"value\":\"sound\"},\"radarAlarmState\": {\"value\":\"disabled\"},\"mobAlarmMethod\": {\"value\":\"sound\"},\"mobAlarmState\": {\"value\":\"disabled\"}},\"steering\":{\"rudderAngle\": {\"value\":0.0000000000},\"rudderAngleTarget\": {\"value\":0.0000000000},\"autopilot\":{\"state\": {\"value\":\"off\"},\"mode\": {\"value\":\"powersave\"},\"targetHeadingNorth\": {\"value\":0.0000000000},\"targetHeadingMagnetic\": {\"value\":0.0000000000},\"alarmHeadingXte\": {\"value\":0.0000000000},\"headingSource\": {\"value\":\"compass\"},\"dead+00:00one\": {\"value\":0.0000000000},\"backlash\": {\"value\":0.0000000000},\"gain\": {\"value\":0},\"maxDriveAmps\": {\"value\":0.0000000000},\"maxDriveRate\": {\"value\":0.0000000000},\"portLock\": {\"value\":0.0000000000},\"starboardLock\": {\"value\":0.0000000000}}},\"environment\":{\"airPressureChangeRateAlarm\": {\"value\":0.0000000000},\"airPressure\": {\"value\":1024.0000000000},\"waterTemp\": {\"value\":0.0000000000},\"wind\":{\"speedAlarm\": {\"value\":0.0000000000},\"directionChangeAlarm\": {\"value\":0.0000000000},\"angleApparent\": {\"value\":0.0000000000},\"directionTrue\": {\"value\":256.3},\"speedApparent\": {\"value\":0.0000000000},\"speedTrue\": {\"value\":7.68}}}}}}");
		DeltaToMapConverter processor = new DeltaToMapConverter();
		SignalKModel output = processor.handle(diff);
		logger.debug(output);
		assertNull(output);
	}
	@Test
	public void shouldIgnoreRandomJson() {
		Json diff = Json.read("{\"headingTrue\": {\"value\": 23,\"source\": \""+SELF+"\",\"timestamp\": \"2014-03-24T00: 15: 41+00:00\" }}");
		DeltaToMapConverter processor = new DeltaToMapConverter();
		SignalKModel output = processor.handle(diff);
		logger.debug(output);
		assertNull(output);
	}
	@Test
	public void shouldProcessComplexDiff() {
		Json diff = Json.read(jsonDiff1);
		DeltaToMapConverter processor = new DeltaToMapConverter();
		SignalKModel output = processor.handle(diff);
		logger.debug(output);
		assertEquals(172.9,(double) output.get(vessels_dot_self_dot+nav_courseOverGroundTrue+"."+VALUE),001);
		assertEquals("2014-08-15T16:00:00.081+00:00", output.get(vessels_dot_self_dot+nav_courseOverGroundTrue+"."+SOURCE+".timestamp"));
		assertEquals("/dev/actisense", output.get(vessels_dot_self_dot+nav_courseOverGroundTrue+"."+SOURCE+".device"));
		
		assertEquals(3.85,(double) output.get(vessels_dot_self_dot+nav_speedOverGround+"."+VALUE),001);
		assertEquals("2014-08-15T16:00:00.081+00:00", output.get(vessels_dot_self_dot+nav_speedOverGround+"."+SOURCE+".timestamp"));
		assertEquals("/dev/actisense", output.get(vessels_dot_self_dot+nav_speedOverGround+"."+SOURCE+".device"));
	}
	
	@Test
	public void shouldProcessDiffArray() {
		Json diff = Json.read(jsonDiff2);
		DeltaToMapConverter processor = new DeltaToMapConverter();
		SignalKModel output = processor.handle(diff);
		logger.debug(output);
		assertEquals(172.9,(double) output.get(vessels_dot_self_dot+nav_courseOverGroundTrue+"."+VALUE),001);
		assertEquals("2014-08-15T16:00:00.081+00:00", output.get(vessels_dot_self_dot+nav_courseOverGroundTrue+"."+SOURCE+".timestamp"));
		assertEquals("/dev/actisense", output.get(vessels_dot_self_dot+nav_courseOverGroundTrue+"."+SOURCE+".device"));
		
		assertEquals(3.85, (double)output.get(vessels_dot_self_dot+nav_speedOverGround+"."+VALUE),001);
		assertEquals("2014-08-15T16:00:00.081+00:00", output.get(vessels_dot_self_dot+nav_speedOverGround+"."+SOURCE+".timestamp"));
		assertEquals("/dev/actisense", output.get(vessels_dot_self_dot+nav_speedOverGround+"."+SOURCE+".device"));
		
		assertEquals(152.9, (double)output.get(vessels_dot_self_dot+nav_courseOverGroundMagnetic+"."+VALUE),001);
		assertEquals("2014-08-15T16:00:00.081+00:00", output.get(vessels_dot_self_dot+nav_courseOverGroundMagnetic+"."+SOURCE+".timestamp"));
		assertEquals("/dev/ttyUSB0", output.get(vessels_dot_self_dot+nav_courseOverGroundMagnetic+"."+SOURCE+".device"));
		
		assertEquals(2.85, (double)output.get(vessels_dot_self_dot+nav_speedThroughWater+"."+VALUE),001);
		assertEquals("2014-08-15T16:00:00.081+00:00", output.get(vessels_dot_self_dot+nav_speedThroughWater+"."+SOURCE+".timestamp"));
		assertEquals("/dev/ttyUSB0", output.get(vessels_dot_self_dot+nav_speedThroughWater+"."+SOURCE+".device"));
	}
}