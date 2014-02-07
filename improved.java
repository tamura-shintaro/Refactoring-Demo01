public class Movie {
   public static final int CHILDRENS = 2;
   public static final int NEW_RELEASE = 1;
   public static final int REGURAR = 0;

   private string _title;
   private int _priceCode;

   public Movie(String title, int priceCode) {
      _title = title;
      _priceCode = priceCode;
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
      double totalAmount = 0;
      int frequentRenterPoints = 0;
      Enumeration rentals = _rentals.elements();
      String result = "Rental records for " + getName() + "\n";
      while(rentals.hasMoreElements()) {
         double thisAmount = 0;
	 Rental each = (Rental) rentals.nextElement();

	 //calculate amount by using "amountFor" method.
	 thisAmount = amountFor(each);

	 frequentRenterPoints ++;

	 if ((each.getMovie().getPriceCode() == Movie.NEW_RELEASE) &&
	     (each.getRentedDays() > 1)) frequentRenterPoints ++;

	 result += "\t" + each.getMovie() + "\t" +
	           String.valueOf(thisAmount) + "\n";
         totalAmount += thisAmount;
      }
      result += "Amount owed is " + String.valueOf(totalAmount) + "\n";
      result += "You earned " + String.valueOf(frequentRenterPoints) +
                " points.";
      return result;
   }

   public double amountFor(Rental each) {
      double thisAmount;
      switch (each.getMovie().getPriceCode()) {
         case Movie.REGURAR:
	    thisAmount += 2;
	    if (each.getDaysRented() > 2)
	       thisAmount += (each.getDaysRented() - 2) * 1.5;
	    break;
	 case Movie.CHILDRENS:
	    thisAmount += 1.5;
	    if (each.getDaysRented() > 3)
	       thisAmount += (each.getDaysRented() - 3) * 1.5;
	    break;
	 case Movie.NEW_RELEASE:
	    thisAmount += each.getDaysRented() * 3;
	    break;
      }
      return thisAmount;
   }
}
