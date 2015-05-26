/*
 * Copyright 2012,2013 Robert Huitema robert@42.co.nz
 * 
 * This file is part of FreeBoard. (http://www.42.co.nz/freeboard)
 * 
 * FreeBoard is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * FreeBoard is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with FreeBoard. If not, see <http://www.gnu.org/licenses/>.
 */

package nz.co.fortytwo.signalk.util;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;

import mjson.Json;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.junit.Test;

public class SGImplifyTest {

	private static Logger logger = Logger.getLogger(SGImplifyTest.class);

	@Test
	public void shouldSimplifyTrack() throws IOException {
		Json trackLine = Json.read(FileUtils.readFileToString(new File("src/test/resources/samples/trackArray.json")));
		Json track = trackLine.at("coordinates");
		logger.debug(track);
		// about 1m at equator = 0.00001
		Json simpleTrack = SGImplify.simplifyLine2D(0.00002, track);
		logger.debug(simpleTrack);
		logger.debug("Track:" + track.asList().size() + ", simpleTrack:" + simpleTrack.asList().size());
		assertTrue(track.asList().size() > simpleTrack.asList().size());
	}

	@Test
	public void shouldHandleArray() {
		Json track = Json
				.read("[[23.5391,60.08451666666666],[23.53908611111111,60.08450833333333],[23.539060648148148,60.08449583333333],[23.539025540123458,60.084479861111106],[23.538982394547325,60.08446099537037],[23.538932551011662,60.084439718364195],[23.53887990362083,60.08441643197016],[23.538822141906248,60.08439147108625],[23.538760118255208,60.08436233701632],[23.538694542990452,60.08433250306916],[23.538628785825377,60.08430208589097],[23.538560099298923,60.08426840490914],[23.53849174941577,60.08423478186873],[23.53841812451314,60.08419565155727],[23.538345659316505,60.08415748740884],[23.538274160541533,60.08411735061848],[23.538203467117942,60.08407556995984],[23.538133444820506,60.08403519718876],[23.538063981794867,60.083993219879524],[23.537994984829055,60.08395268323294],[23.537926376246435,60.08391056936078],[23.537858091316473,60.08386714113398],[23.53779007609706,60.083825395389425],[23.537725063414218,60.08378227393563],[23.537659775067404,60.083738006057466],[23.537594257000617,60.083695560603445],[23.53752854750051,60.083651856058424],[23.537465456250427,60.08360710227091],[23.537401769097578,60.083564251892426],[23.537337585359094,60.083520209910354],[23.537272987799245,60.08347795270308],[23.53721082316604,60.083434405030346],[23.537147908193923,60.08338978196973],[23.53708436793938,60.083344262752554],[23.537017528838373,60.08329521896046],[23.536950718476422,60.083248793578164],[23.536886709841465,60.08320177242625],[23.536825035979,60.08315425479965],[23.5367625299825,60.083106323444156],[23.53670210831875,60.083060825092346],[23.53664342359896,60.08301457646584],[23.53658340855469,60.08296770261042],[23.536525062684465,60.08292030773091],[23.53646810779261,60.08287525644242],[23.5364123120494,60.08282938036868],[23.53635748226339,60.082782816973896],[23.53630067966394,60.082738458589354],[23.53624501083106,60.08269315993557],[23.536190286803663,60.08264707772409],[23.536136350114162,60.08260034254785],[23.53608306953958,60.082553063234315],[23.53603033572743,60.08250810825082],[23.535975279772856,60.08246231243124],[23.535921066477382,60.08241581591492],[23.535867555397818,60.08236873548466],[23.535809073942623,60.082318390681664],[23.535752006063298,60.082268103345825],[23.535696116163862,60.082220641677075],[23.53564120791433,60.08217275695311],[23.535584339928608,60.08212451968315],[23.535528616607174,60.082075988624844],[23.535473847172646,60.08202721274292],[23.535419872643875,60.0819810106191],[23.535366560536563,60.08193417551592],[23.535311022669358,60.08188681292993],[23.53525640778002,60.08184178855272],[23.535199784261128,60.081795934905045],[23.535144264662048,60.08174939019865],[23.535089664996153,60.08170504738777],[23.53503583194124,60.08165976171203],[23.534982637728813,60.081613690315585],[23.534927198107344,60.081566964151875],[23.534872665089456,60.08152247012656],[23.53481610979677,60.08147705843879],[23.534757869275083,60.0814336598101],[23.534695446618127,60.08138638317509],[23.534635094403995,60.08133865264591],[23.534573689781105,60.08129054387159],[23.534514185928696,60.081244897670764],[23.53445348827391,60.08119852583675],[23.534394573561592,60.081151549308395],[23.53433714463466,60.0811040688681],[23.534280953862215,60.08105616850119],[23.53422579488518,60.08101069597321],[23.534171495737652,60.08096446886656],[23.534117913114713,60.080917612944354],[23.534064927595594,60.08087023300918],[23.534012439662995,60.080822416396536],[23.533963144163607,60.080774235886],[23.53391373124745,60.080728529904995],[23.533866998261765,60.08068210825416],[23.533819720773693,60.0806350902118],[23.533771989533633,60.0805875751765],[23.533726657944694,60.080539645980416],[23.533680548287244,60.080491371650346],[23.533633790239367,60.08044280970862],[23.53358649186614,60.08039123031274],[23.533538743221783,60.08033991414951],[23.533490619351486,60.08028881734682],[23.533444960570684,60.080237903344575],[23.533398578253347,60.080187141676035],[23.5333515929889,60.08013928473002],[23.533306883046308,60.08009107060835],[23.53326129142748,60.08004255884029],[23.533214965078457,60.07999379903357],[23.53316802645427,60.07994483252798],[23.533123355378557,60.07989569377332],[23.533077796148795,60.07984641147777],[23.533031496790663,60.07979700956481],[23.532984580658884,60.07974750797067],[23.532939928326847,60.079700701086665],[23.53289438471682,60.07965336201667],[23.532848098375126,60.07960557945833],[23.532801193090382,60.07955742732639],[23.532753772019763,60.07950896721644],[23.53270592112758,60.07946025045815],[23.53266048982854,60.079411319826235],[23.53261151930156,60.07935943318853],[23.532562377195745,60.07930786099044],[23.532513092107564,60.07925655082536],[23.532463687867416,60.07920545902113],[23.53241418433396,60.079157326962054],[23.532364598056077,60.07910888357949],[23.53231494282451,60.079060180760685],[23.532265230131536,60.079011261745016],[23.532215469554057,60.07896216256529],[23.532165669072825,60.07891291324885],[23.532118613116243,60.07886353881848],[23.53207106648576,60.07881683790429],[23.532025888738133,60.07876958714247],[23.531979907281777,60.07872187817428],[23.531933256068147,60.07867378736746],[23.531886046723454,60.07862537836177],[23.531838372269544,60.07857948196814],[23.53179031022462,60.078532901640116],[23.53174192518718,60.07848575136676],[23.531693270989315,60.07843812613897],[23.53164161471332,60.07839010511581],[23.531587456705545,60.07833897648539],[23.531533991699064,60.07829081373782],[23.531481104193663,60.07824234478152],[23.531428697939162,60.07819362065127],[23.531373914949302,60.078144683876054],[23.531319929124418,60.07809834767449],[23.53126660760368,60.078051400839854],[23.531213839669732,60.078003945144324],[23.53116153305811,60.07795606539805],[23.531112388659537,60.07790783227615],[23.531063101660727,60.077859304674575],[23.531013695828385,60.07781330945104],[23.5309641909681,60.07776664676476],[23.530914603584527,60.07771942785952],[23.53086494743155,60.077671745438494],[23.530815233970735,60.0776236767543],[23.53076547275339,60.07757806396192],[23.530715671738935,60.07753171996826],[23.53066583756022,60.077484766640225],[23.530615975744627,60.07743730553352],[23.5305660908983,60.077389421277935],[23.530513409081916,60.0773384066205],[23.53046117423493,60.07728756107264],[23.53040931186244,60.0772396342272],[23.530357759885366,60.077191361856],[23.53030646657114,60.07714280154667],[23.530255388809284,60.07709400128889],[23.53020726845218,60.077045001074076],[23.53015883482126,60.0769958342284],[23.530110140128826,60.076949306301444],[23.53006122788513,60.07690219969565],[23.53001213434872,60.07685461085749],[23.529962889735042,60.07680662015902],[23.529913519223648,60.07675829457696],[23.529864043797485,60.076709689925245],[23.52981448094235,60.07666363049326],[23.529764845229735,60.07661691429993],[23.52971514880256,60.076569650805496],[23.529665401779912,60.07652193122681],[23.52961561259437,60.0764738315779],[23.529568566050862,60.07642541520381],[23.529521027264607,60.07637951266984],[23.529467522720505,60.07633014944709],[23.52941460226709,60.0762806800948],[23.529364946333683,60.07623112230122],[23.529315233055843,60.076181490806576],[23.529265471990982,60.07613179789437],[23.529215671103596,60.07608205380086],[23.529165837030774,60.07603504483405],[23.529115975303423,60.07598753736171],[23.52906609053063,60.0759396144681],[23.52901896433108,60.07589134539008],[23.52897135916479,60.07584278782507],[23.528923354859543,60.075796767632],[23.528875017938507,60.07575008413778],[23.528826403837645,60.0757028478926],[23.52878033653137,60.075655151021614],[23.52873361377614,60.07560707029579],[23.52868634481345,60.075558669690935],[23.528638620677874,60.075510002520225],[23.52859051723156,60.07546389098907],[23.528544875470747,60.07541713137978],[23.528495729558955,60.07536427614981],[23.528446441299128,60.07531467456929],[23.528397034415942,60.07526500658552],[23.528350306457728,60.075215283265706],[23.52830303315922,60.07516551383253],[23.52825530541046,60.075115705971555],[23.52820719895316,60.07506586608741],[23.52816155468319,60.075015999517284],[23.528115184458215,60.07496888848662],[23.528068209270735,60.074921295961076],[23.528020729947837,60.074873302189786],[23.527975608289864,60.07482497404705],[23.527929673574885,60.07477636726143],[23.5278830613124,60.074727528273414],[23.527835884427,60.07468127356118],[23.527788237022502,60.07463439463432],[23.52774297529653,60.074586995528605],[23.52769692385822,60.0745391629405],[23.527650214326293,60.074490969117086],[23.527605734160797,60.07444247426424],[23.527560334022887,60.07439372855353],[23.527511389463516,60.07434199601683],[23.527462268997372,60.07429055223624],[23.527413001942254,60.07423934908575],[23.527366390507435,60.074191124238126],[23.527319214311753,60.07414260353177],[23.527271567482018,60.074093836276475],[23.527223528457238,60.07404486356373],[23.527175162603253,60.073995719636436],[23.527126524391598,60.07394921080814],[23.52707765921522,60.07390212011789],[23.52702860490157,60.07385454454269],[23.52697939297353,60.073806564896685],[23.526930049700166,60.0737610263028],[23.52688059697236,60.07371474414122],[23.526831053032524,60.07366784233991],[23.52678143308266,60.07362042417215],[23.526731749791107,60.07357257569901],[23.52668201371481,60.07352714641584],[23.52663223365123,60.07348095534654]]");
		logger.debug(track);
		// about 1m at equator = 0.00001
		Json simpleTrack = SGImplify.simplifyLine2D(0.00002, track);
		logger.debug(simpleTrack);
		logger.debug("Track:" + track.asList().size() + ", simpleTrack:" + simpleTrack.asList().size());
		assertTrue(track.asList().size() > simpleTrack.asList().size());
	}

}