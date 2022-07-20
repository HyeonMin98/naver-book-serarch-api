package parser;

import com.lhm.ex_naverapi.NaverActivity;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

import vo.BookVO;

public class Parser {
    //웹에서 요소 (제목,저자 , 이미지, 가격)을 검색하여 vo에 저장 및 List에 저장한다.
    BookVO vo;
    String myQuery = ""; //검색어를 의미


    public ArrayList<BookVO> connectNaver(){
        ArrayList<BookVO> list = new ArrayList<>();

        try{
            //검색어 (myQuery)를 UTF-8 형태로 인코딩
            myQuery = URLEncoder.encode(NaverActivity.search.getText().toString(),"UTF-8");

            String urlstr = "https://openapi.naver.com/v1/search/blog.xml?query="+myQuery+"&display=100";

            URL url = new URL(urlstr);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            //발급받은 ID와 Secret을 전달.
            connection.setRequestProperty("X-Naver-Client-Id","hgGFbvIpeCIH8drJZx_Y");
            connection.setRequestProperty("X-Naver-Client-Secret","H1XldEQSUW");

            //위의 URL을 수행하여 받은 자원을 자바코드로 파싱
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            XmlPullParser parser = factory.newPullParser();

            //connection 객체가 접속후 가지게 된 내용을 parser가 스트림으로 읽어온다.
            parser.setInput(connection.getInputStream(), null);

            //파서 객체를 통해 각 요소별 접근을 하게되고, 태그 (요소)내부의 값들을 가져온다.

            //while문을 돌리면서 더잇아 읽어올 책이 없을때까지 모든 정보를 다 가져올 것이다.
            int parserEvent = parser.getEventType();


            while(parserEvent != XmlPullParser.END_DOCUMENT) {   //문서의 끝
                //서버쪽 xml문서의 끝을 만날때 까지 while문이 반복

                //시작 태그(title,price...)의 이름을 가져와 vo에 담을 수 있는 정보라면 vo에 추가
                if(parserEvent == XmlPullParser.START_TAG){
                    String tagName = parser.getName();

                    if(tagName.equals("title")){
                        vo = new BookVO();
                        String title = parser.nextText();
                        vo.setB_title(title);
                    }else if(tagName.equals("image")){
                        String img = parser.nextText();
                        vo.setB_img(img);
                    }else if(tagName.equals("author")){
                        String author = parser.nextText();
                        vo.setB_author(author);
                    }else if(tagName.equals("price")){
                        String price = parser.nextText();
                        vo.setB_price(Integer.parseInt(price));
                    }
                    list.add(vo); //마지막 정보인 price까지 찾고난 뒤 list에 저장.
                }
                parserEvent = parser.next(); //다음 요소를 가져올때 순서대로 가져와야한다.
            }//while


        }catch(Exception e){

        }
        return list;

    }



}
