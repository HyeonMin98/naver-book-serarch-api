package vo;

public class BookVO {
    //네이버 도서 서버에서 얻어올 정보를 저장할 변수
    //제목, 저자, 가격, 이미지(URL, 있는경우만 나타남)
    private String b_title, b_author,b_img, b_price ;
//    private int ;


    public String getB_title() {
        return b_title;
    }

    public void setB_title(String b_title) {
        this.b_title = b_title;
    }

    public String getB_author() {
        return b_author;
    }

    public void setB_author(String b_author) {
        this.b_author = b_author;
    }

    public String getB_img() {
        return b_img;
    }

    public void setB_img(String b_img) {
        this.b_img = b_img;
    }

    public String getB_price() {
        return b_price;
    }

    public void setB_price(String b_price) {
        this.b_price = b_price;
    }
}
