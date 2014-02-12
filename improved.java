public class Movie {
   public static final int CHILDRENS = 2;
   public static final int NEW_RELEASE = 1;
   public static final int REGURAR = 0;

   private string _title;
   private Price _price;

   public Movie(String title, int priceCode) {
      _title = title;
      setPriceCode(priceCode);
   }

   public int getPriceCode() {
      return _price.getPriceCode();
   }

   public void setPriceCode(int arg) {
      swich(arg) {
         case REGURAR:
	    _price = new RegularPrice();
	    break;
	 case NEW_RELEASE:
	    _price = new NewReleasePrice();
	    break;
	 case CHILDRENS:
	    _price = new ChildrensPrice();
	    break;
	 default:
	    throw new IllegalArgumentException("Illegal Code");
      }
   }

   public string getTitle() {
      return _title;
   }

   double getCharge(int daysRented) {
      return _price.getCharge(daysRented);
   }

   int getFrequentRenterPoints(int daysRented) {
      return _price.getFrequentRenterPoints(daysRented);
   }
}

class Price {
   abstract int getPriceCode();

   abstract double getCharge(int daysRented);

   int getFrequentRenterPoints(int daysRented) {
      return 1;
   }
}

class ChildrensPrice extends Price {
   int getPriceCode() {
      return Movie.CHILDRENS;
   }
   double getCharge(int daysRented) {
      double result = 1.5;
      if (daysRented > 3)
         result += (daysRented - 3) * 1.5;
      return result;
   }
}

class NewReleasePrice extends Price {
   int getPriceCode() {
      return Movie.NEW_RELEASE;
   }
   double getCharge(int daysRented) {
      return daysRented * 3;
   }
   int getFrequentRenterPoints(int daysRented) {
      return (daysRented > 1) ? 2 : 1;
   }
}

class RegularPrice extends Price {
   int getPriceCode() {
      return Movie.REGURAR;
   }
   double getCharge(int daysRented) {
      double result = 2;
      if (daysRented > 2)
         result += (daysRented - 2) * 1.5;
      return result;
   }
}


class Rental {
   private Movie _movie;
   private int _daysRented;

   public Rental(Movie movie, int daysRented) {
      _movie = movie;
      _daysRented = daysRented;
   }

   public int getDaysRented() {
      return _daysRented;
   }

   public Movie getMovie() {
      return _movie;
   }

   double getCharge() {
      return _movie.getCharge(_daysRented);
   }

   int getFrequentRenterPoints() {
      return _movie.getFrequentRenterPoints(_daysRented);
   }
}

class Customer {
   private String _name;
   private Vector _rentals = new Vector();

   public Customer(String name) {
      _name = name;
   }

   public void addRentals(Rental arg) {
      _rentals.addElement(arg);
   }

   public String getName() {
      return _name;
   }

   public String statement() {
      Enumeration rentals = _rentals.elements();
      String result = "Rental records for " + getName() + "\n";
      while(rentals.hasMoreElements()) {
	 Rental each = (Rental) rentals.nextElement();

	 result += "\t" + each.getMovie().getTitle() + "\t" +
	           String.valueOf(each.getCharge()) + "\n";
      }
      result += "Amount owed is " + String.valueOf(getTotalCharge()) + "\n";
      result += "You earned " + String.valueOf(getTotalFrequentRenterPoints()) +
                " points.";
      return result;
   }

   public String htmlStatement() {
      Enumeration rentals = _rentals.elements();
      String result = "<H1>Rentals for <EM>" + getName() + "</EM></H1><P>\n";
      while(rentals.hasMoreElements()) {
	 Rental each = (Rental) rentals.nextElement();

	 result += each.getMovie().getTitle() + ": " +
	           String.valueOf(each.getCharge()) + "<BR>\n";
      }
      result += "<P>You owe <EM>" + String.valueOf(getTotalCharge()) + "</EM><P>\n";
      result += "You earned <EM>" + String.valueOf(getTotalFrequentRenterPoints()) +
                "</EM> points.";
      return result;
   }

   private double getTotalCharge() {
      double result = 0;
      Enumeration rentals = _rentals.elements();
      while(rentals.hasMoreElements()) {
	 Rental each = (Rental) rentals.nextElement();
	 result += each.getCharge();
      }
      return result;
   }

   private int getTotalFrequentRenterPoints() {
      int result = 0;
      Enumeration rentals = _rentals.elements();
      while(rentals.hasMoreElements()) {
	 Rental each = (Rental) rentals.nextElement();
	 result += each.getFrequentRenterPoints();
      }
      return result;
   }
}
