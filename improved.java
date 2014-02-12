public class Movie {
   public static final int CHILDRENS = 2;
   public static final int NEW_RELEASE = 1;
   public static final int REGURAR = 0;

   private string _title;
   private int _priceCode;

   public Movie(String title, int priceCode) {
      _title = title;
      setPriceCode(priceCode);
   }

   public int getPriceCode() {
      return _priceCode;
   }

   public void setPriceCode(int arg) {
      _priceCode = arg;
   }

   public string getTitle() {
      return _title;
   }

   double getCharge(int daysRented) {
      double result = 0;
      switch (getPriceCode()) {
         case Movie.REGURAR:
	    result += 2;
	    if (daysRented > 2)
	       result += (daysRented - 2) * 1.5;
	    break;
	 case Movie.CHILDRENS:
	    result += 1.5;
	    if (daysRented > 3)
	       result += (daysRented - 3) * 1.5;
	    break;
	 case Movie.NEW_RELEASE:
	    result += daysRented * 3;
	    break;
      }
      return result;
   }

   int getFrequentRenterPoints() {
      if (getPriceCode() == Movie.NEW_RELEASE && daysRented > 1)
         return 2;
      else
         return 1;
   }
}

abstract class Price {
   abstract int getPriceCode();
}
class ChildrensPrice extends Price {
   int getPriceCode() {
      return Movie.CHILDRENS;
   }
}
class NewReleasePrice extends Price {
   int getPriceCode() {
      return Movie.NEW_RELEASE;
   }
}
class RegularPrice extends Price {
   int getPriceCode() {
      return Movie.REGURAR;
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
