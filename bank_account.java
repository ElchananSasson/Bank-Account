import java.util.Scanner;

public class bank_account {

	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		int menuChoose;
		print_menu();
		menuChoose = scan.nextInt();
		while (menuChoose != 6) {
			if (menuChoose < 1 || menuChoose > 6) {
				System.out.println("Please select an existing option.");
				menuChoose = scan.nextInt();
			}
			if (menuChoose == 1 || menuChoose == 3) {
				double amount;
				String date, type, description;
				System.out.println("Please enter the date(format: YYYY.MM.DD):");
				scan.nextLine();
				date = scan.nextLine();
				if (menuChoose == 1) {
					System.out.println("Please enter how much money you would like to expense:");
					amount = scan.nextDouble();
				} else {
					System.out.println("Please enter how much money you would like to income:");
					amount = scan.nextDouble();
				}
				System.out.println("What type of expenditure? (cash, credit, check, etc.)?");
				scan.nextLine();
				type = scan.nextLine();
				double balance = DB.getBalance();
				if (menuChoose == 1) {
					System.out.println("Reason for expense?");
					// scan.nextLine();
					description = scan.nextLine();
					if (amount > balance) {
						char ans;
						System.out.println("This expense will make you go into minus, would you like to continue anyway? (Y/N)");
						//scan.nextLine();
						ans = scan.next().charAt(0);
						if (ans == 'y' || ans == 'Y') {
							DB.insert("out", amount * (-1), type, description, date, balance);
						}
					} else {
						DB.insert("out", amount * (-1), type, description, date, balance);
					}
				} else {
					System.out.println("Reason for income?");
					// scan.nextLine();
					description = scan.nextLine();
					DB.insert("in", amount, type, description, date, balance);
				}
				print_menu();
				menuChoose = scan.nextInt();
			}
			if (menuChoose == 2 || menuChoose == 4) {
				int sec_menu_choose;
				if (menuChoose == 2) {
					print_exp_menu();
				} else {
					print_inc_menu();
				}
				sec_menu_choose = scan.nextInt();
				while (sec_menu_choose < 1 || sec_menu_choose > 4) {
					System.out.println("Please select an existing option.");
					sec_menu_choose = scan.nextInt();
				}
				if (sec_menu_choose == 1) {
					String date;
					System.out.println("Please insert a year and month:(format: YYYY.MM)");
					scan.nextLine();
					date = scan.nextLine();
					if (menuChoose == 2) {
						DB.print_operations_inMonth("out", date);
					} else {
						DB.print_operations_inMonth("in", date);
					}
				}
				if (sec_menu_choose == 2) {
					String sec_type;
					System.out.println("What is the type of payment?");
					scan.nextLine();
					sec_type = scan.nextLine();
					if (menuChoose == 2) {
						DB.print_type("out", sec_type);
					} else {
						DB.print_type("in", sec_type);
					}
				}
				if (sec_menu_choose == 3) {
					int bigger;
					System.out.println("bigger than:");
					bigger = scan.nextInt();
					if (menuChoose == 2) {
						DB.print_bigger_smaller("out", "big", bigger);
					} else {
						DB.print_bigger_smaller("in", "big", bigger);
					}
				}
				if (sec_menu_choose == 4) {
					int smaller;
					System.out.println("smaller than:");
					smaller = scan.nextInt();
					if (menuChoose == 2) {
						DB.print_bigger_smaller("out", "small", smaller);
					} else {
						DB.print_bigger_smaller("in", "small", smaller);
					}
				}
				print_menu();
				menuChoose = scan.nextInt();
			}
			if (menuChoose == 5) {
				double balance = DB.getBalance();
				System.out.println(balance);
				print_menu();
				menuChoose = scan.nextInt();
			}
		}
		//DB.reset();
		System.out.println("See you next time !");
		scan.close();
	}

	public static void print_menu() {
		System.out.println("What do you want to do?");
		System.out.println("1. Enter new expense");
		System.out.println("2. Watch expenses");
		System.out.println("3. Enter new income");
		System.out.println("4. Watch incomes");
		System.out.println("5. Show account balance");
		System.out.println("6. Exit");
	}

	public static void print_exp_menu() {
		System.out.println("What details about expenses would you like to receive?");
		System.out.println("1. Expenses for a particular month");
		System.out.println("2. Expenses by which type?");
		System.out.println("3. Bigger expenses from:");
		System.out.println("4. Smaller expenses from:");
	}

	public static void print_inc_menu() {
		System.out.println("What income details would you like to receive?");
		System.out.println("1. Income for a particular month");
		System.out.println("2. Income by which type?");
		System.out.println("3. Bigger income from:");
		System.out.println("4. Smaller income from:");
	}
}
