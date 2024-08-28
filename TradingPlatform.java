import java.util.*;
public class TradingPlatform {
	public static double Random() {
		List<Double> list=new ArrayList<>(Arrays.asList(1.0,2.0,3.0,4.0,5.0,6.0,7.0,8.0,9.0,-1.0,-2.0,-3.0,-4.0,-5.0,-6.0,-7.0,-8.0,-9.0));
		Random rand=new Random();
		int index = rand.nextInt(list.size());
		return list.get(index);
	}
	public static Map<String,Double> Stock(Double cash){
		Map<String,Double> data=new HashMap<>();
		data.put("Reliance", 3037.45+Random());
		data.put("HDFC", 1643.00+Random());
		data.put("TCS", 4519.38+Random());
		data.put("Airtel", 1518.25+Random());
		data.put("Cash", cash);
		return data;
	}
	private static Map<String,Integer> BuyStock(String symbol,int quantity,Map<String,Double> data,Map<String,Integer> holdings) {
		Double cash=data.get("Cash");
		Double price=data.get(symbol);
		Double bought=quantity*price;
		if(bought<cash) {
			data.put("Cash", cash-bought);
			if(holdings.containsKey(symbol)) {
				holdings.put(symbol, holdings.get(symbol)+quantity);
			}
			else
				holdings.put(symbol,quantity);
			System.out.println(quantity+" stock of "+symbol+" is bought!");
		}
		else
			System.out.println("Not Enough Cash!");
		return holdings;
	}
	private static Map<String,Integer> SellStock(String symbol,int quantity,Map<String,Double> data,Map<String,Integer> holdings) {
		System.out.println(quantity+" stock of "+symbol+" is sold!");
		Double cash=data.get("Cash");
		Double price=data.get(symbol);
		Double sold=quantity*price;
		data.put("Cash", cash+sold);
		for(String stock: holdings.keySet()) {
			if(stock.equals(symbol)) {
				if(holdings.get(stock) !=quantity)
					holdings.put(symbol,holdings.get(stock)-quantity);
				else
					holdings.remove(stock);
			}
		}
		return holdings;
	}
	public static void main(String[] args) {
		Map<String,Map<String,Double>> marketData=new HashMap<>();
		Map<String,Double> data=new HashMap<>();
		Double cash=10000.0;
		data=Stock(cash);
		marketData.put("Market Data",data);
		Map<String,Integer> holdings=new HashMap<>();
		Scanner sc=new Scanner(System.in);
		int option=-1;
		while(option!=3) {
		data=Stock(data.get("Cash"));
		for(String n: marketData.keySet()) {
			Map<String,Double>ref=new HashMap<>();
			ref=marketData.get(n);
			System.out.println("===========================\n"+n);
			for(String key: ref.keySet()) {
			System.out.println(key+" = "+ref.get(key));
			}
		}
		System.out.println("Holdings");
		for(String hold:holdings.keySet()) {
			System.out.println("   "+hold+" : "+holdings.get(hold));
		}
		System.out.println("Enter Option 1)Buy Stock 2)Sell Stock 3)Exit");
		option= sc.nextInt();
		sc.nextLine();
		String symbol;
		int quantity;
		if(option<4) {
			if(option<3) {
				System.out.print("Enter Stock Name: ");
				symbol=sc.nextLine();
				System.out.print("Enter Stock quantity: ");
				quantity=sc.nextInt();
				if(option==1) {
					holdings=BuyStock(symbol,quantity,data,holdings);
				}
				if(option==2) {
					if(holdings.containsKey(symbol)) {
						holdings=SellStock(symbol,quantity,data,holdings);
					}
					else
						System.out.println("No stock in "+symbol+" to sell!");
				}
				marketData.put("Market Data",data);
			}
				
		}
		else
			System.out.println("Enter a valid option");
	}
	sc.close();
		}
}
