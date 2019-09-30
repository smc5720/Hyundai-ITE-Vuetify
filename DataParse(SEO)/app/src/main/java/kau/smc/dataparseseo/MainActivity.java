package kau.smc.dataparseseo;

import android.app.Activity;
import android.os.Bundle;
import android.os.StrictMode;
import android.widget.TextView;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.net.URL;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        StrictMode.enableDefaults();

        String MyKey = "DiybFgVPPjHrKnOz50ynIQf%2BdWtI9Tl%2B2lOyvZ%2BXjzGCm9x8dKca9Hip9wb16aAoypDcGOV047R0cDuVB0HpeQ%3D%3D";
        String numOfRows = "10";

        TextView status1 = (TextView) findViewById(R.id.result); //파싱된 결과확인!

        boolean initem = false, inbuildAddress = false, inbuildPlace = false, inmanager = false, inmanagerTel = false,
                inmfg = false, inmodel = false, inorg = false, inrnum = false, inwgs84Lat = false,
                inwgs84Lon = false, inzipcode1 = false, inzipcode2 = false;

        String buildAddress = null, buildPlace = null, manager = null, managerTel = null,
                mfg = null, model = null, org = null, rnum = null, wgs84Lat = null, wgs84Lon = null,
                zipcode1 = null, zipcode2 = null;


        try {
            URL url = new URL("http://apis.data.go.kr/B552657/AEDInfoInqireService/getAedFullDown?serviceKey="
                    + MyKey + "&pageNo=1&numOfRows=" + numOfRows); //검색 URL부분

            XmlPullParserFactory parserCreator = XmlPullParserFactory.newInstance();
            XmlPullParser parser = parserCreator.newPullParser();

            parser.setInput(url.openStream(), null);

            int parserEvent = parser.getEventType();
            System.out.println("파싱시작합니다.");

            while (parserEvent != XmlPullParser.END_DOCUMENT) {
                switch (parserEvent) {
                    case XmlPullParser.START_TAG://parser가 시작 태그를 만나면 실행
                        if (parser.getName().equals("buildAddress")) { //title 만나면 내용을 받을수 있게 하자
                            inbuildAddress = true;
                        }
                        if (parser.getName().equals("buildPlace")) { //address 만나면 내용을 받을수 있게 하자
                            inbuildPlace = true;
                        }
                        if (parser.getName().equals("manager")) { //mapx 만나면 내용을 받을수 있게 하자
                            inmanager = true;
                        }
                        if (parser.getName().equals("managerTel")) { //mapy 만나면 내용을 받을수 있게 하자
                            inmanagerTel = true;
                        }
                        if (parser.getName().equals("mfg")) { //mapy 만나면 내용을 받을수 있게 하자
                            inmfg = true;
                        }
                        if (parser.getName().equals("model")) { //mapy 만나면 내용을 받을수 있게 하자
                            inmodel = true;
                        }
                        if (parser.getName().equals("org")) { //mapy 만나면 내용을 받을수 있게 하자
                            inorg = true;
                        }
                        if (parser.getName().equals("rnum")) { //mapy 만나면 내용을 받을수 있게 하자
                            inrnum = true;
                        }
                        if (parser.getName().equals("wgs84Lat")) { //mapy 만나면 내용을 받을수 있게 하자
                            inwgs84Lat = true;
                        }
                        if (parser.getName().equals("wgs84Lon")) { //mapy 만나면 내용을 받을수 있게 하자
                            inwgs84Lon = true;
                        }
                        if (parser.getName().equals("zipcode1")) { //mapy 만나면 내용을 받을수 있게 하자
                            inzipcode1 = true;
                        }
                        if (parser.getName().equals("zipcode2")) { //mapy 만나면 내용을 받을수 있게 하자
                            inzipcode2 = true;
                        }
                        if (parser.getName().equals("message")) { //message 태그를 만나면 에러 출력
                            status1.setText(status1.getText() + "에러");
                            //여기에 에러코드에 따라 다른 메세지를 출력하도록 할 수 있다.
                        }
                        break;

                    case XmlPullParser.TEXT://parser가 내용에 접근했을때
                        if (inbuildAddress) { //isTitle이 true일 때 태그의 내용을 저장.
                            buildAddress = parser.getText();
                            inbuildAddress = false;
                        }
                        if (inbuildPlace) { //isAddress이 true일 때 태그의 내용을 저장.
                            buildPlace = parser.getText();
                            inbuildPlace = false;
                        }
                        if (inmanager) { //isMapx이 true일 때 태그의 내용을 저장.
                            manager = parser.getText();
                            inmanager = false;
                        }
                        if (inmanagerTel) { //isMapy이 true일 때 태그의 내용을 저장.
                            managerTel = parser.getText();
                            inmanagerTel = false;
                        }
                        if (inmfg) { //isMapy이 true일 때 태그의 내용을 저장.
                            mfg = parser.getText();
                            inmfg = false;
                        }
                        if (inmodel) { //isMapy이 true일 때 태그의 내용을 저장.
                            model = parser.getText();
                            inmodel = false;
                        }
                        if (inorg) { //isMapy이 true일 때 태그의 내용을 저장.
                            org = parser.getText();
                            inorg = false;
                        }
                        if (inrnum) { //isMapy이 true일 때 태그의 내용을 저장.
                            rnum = parser.getText();
                            inrnum = false;
                        }
                        if (inwgs84Lat) { //isMapy이 true일 때 태그의 내용을 저장.
                            wgs84Lat = parser.getText();
                            inwgs84Lat = false;
                        }
                        if (inwgs84Lon) { //isMapy이 true일 때 태그의 내용을 저장.
                            wgs84Lon = parser.getText();
                            inwgs84Lon = false;
                        }
                        if (inzipcode1) { //isMapy이 true일 때 태그의 내용을 저장.
                            zipcode1 = parser.getText();
                            inzipcode1 = false;
                        }
                        if (inzipcode2) { //isMapy이 true일 때 태그의 내용을 저장.
                            zipcode2 = parser.getText();
                            inzipcode2 = false;
                        }
                        break;
                    case XmlPullParser.END_TAG:
                        if (parser.getName().equals("item")) {
                            status1.setText(status1.getText() +
                                    "건물주소 : "            + buildAddress     +
                                    "\n 상세주소: "          + buildPlace       +
                                    "\n 담당자 : "           + manager          +
                                    "\n 담당자 번호 : "      + managerTel       +
                                    "\n 기업명 : "           + mfg              +
                                    "\n AED 제품명 : "       + model            +
                                    "\n 건물이름 : "         + org              +
                                    "\n 열 번호 : "          + rnum             +
                                    "\n 위도 : "             + wgs84Lat         +
                                    "\n 경도 : "             + wgs84Lon         +
                                    "\n 우편번호1 : "        + zipcode1         +
                                    "\n 우편번호2 : "        + zipcode2         + "\n\n\n");
                            initem = false;
                        }
                        break;
                }
                parserEvent = parser.next();
            }
        } catch (Exception e) {
            status1.setText("에러가..났습니다...: " + e.getMessage());
        }
    }
}