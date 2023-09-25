/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import Model.Fruit;
import Model.Order;
import java.util.ArrayList;
import java.util.Hashtable;

/**
 *
 * @author DELL
 */
public class Manager {

    Validation Validation = new Validation();

    public int menu() {
        System.out.println("1. Create Fruit");
        System.out.println("2. View orders");
        System.out.println("3. Shopping (for buyer)");
        System.out.println("4. Exit");
        System.out.print("Enter your choice: ");
        int choice = Validation.checkInputIntLimit(1, 4);
        return choice;

    }

    //allow user create fruit
    public void createFruit(ArrayList<Fruit> lf) {
        //loop until user don't want to create fruit
        while (true) {
            System.out.print("Enter fruit id: ");
            String id = Validation.checkInputString();
            //check id exist
            if (!Validation.checkIdExist(lf, id)) {
                System.out.println("Id exist");
                return;
            }
            System.out.print("Enter fruit name: ");
            String name = Validation.checkInputString();
            System.out.print("Enter price: ");
            double price = Validation.checkInputDouble();
            System.out.print("Enter quantity: ");
            int quantity = Validation.checkInputInt();
            System.out.print("Enter origin: ");
            String origin = Validation.checkInputString();
            lf.add(new Fruit(id, name, price, quantity, origin));
            //check user want to continue or not
            if (!Validation.checkInputYN()) {
                return;
            }
        }
    }

    //allow user show view order
    public void viewOrder(Hashtable<String, ArrayList<Order>> ht) {
        for (String name : ht.keySet()) {
            System.out.println("Customer: " + name);
            ArrayList<Order> listOrder = ht.get(name);
            displayListOrder(listOrder);
        }
    }

    //allow user buy items
    public void shopping(ArrayList<Fruit> lf, Hashtable<String, ArrayList<Order>> ht) {
        //check list empty user can't buy
        if (lf.isEmpty()) {
            System.err.println("No have item.");
            return;
        }
        //loop until user don't want to buy continue
        ArrayList<Order> listOrder = new ArrayList<>();
        while (true) {
            displayListFruit(lf);
            System.out.print("Enter item: ");
            int item = Validation.checkInputIntLimit(1, lf.size());
            Fruit fruit = getFruitByItem(lf, item);
            System.out.print("Enter quantity: ");
            int quantity = Validation.checkInputIntLimit(1, fruit.getQuantity());
            fruit.setQuantity(fruit.getQuantity() - quantity);
            //check item exist or not
            if (!Validation.checkItemExist(listOrder, fruit.getId())) {
                updateOrder(listOrder, fruit.getId(), quantity);
            } else {
                listOrder.add(new Order(fruit.getId(), fruit.getName(),
                        quantity, fruit.getPrice()));
            }

            if (!Validation.checkInputYN()) {
                break;
            }
        }
        displayListOrder(listOrder);
        System.out.print("Enter name: ");
        String name = Validation.checkInputString();
        ht.put(name, listOrder);
        System.err.println("Add successfull");
    }

    //display list fruit in shop
    public void displayListFruit(ArrayList<Fruit> lf) {
        int countItem = 1;
        System.out.printf("%-10s%-20s%-20s%-15s\n", "Item", "Fruit name", "Origin", "Price");
        for (Fruit fruit : lf) {
            //check shop have item or not 
            if (fruit.getQuantity() != 0) {
                System.out.printf("%-10d%-20s%-20s$%-15.2f\n", countItem++,
                        fruit.getName(), fruit.getOrigin(), fruit.getPrice());
            }
        }
    }

    //get fruint user want to by
    public Fruit getFruitByItem(ArrayList<Fruit> lf, int item) {
        int countItem = 1;
        for (Fruit fruit : lf) {
            //check shop have item or not 
            if (fruit.getQuantity() != 0) {
                countItem++;
            }
            if (countItem - 1 == item) {
                return fruit;
            }
        }
        return null;
    }

    //display list order
    public void displayListOrder(ArrayList<Order> lo) {
        double total = 0;
        System.out.printf("%-15s%-15s%-15s%-15s\n", "Product", "Quantity", "Price", "Amount");
        for (Order order : lo) {
            System.out.printf("%-15s%-15d%-15.0f$%-15.0f\n", order.getName(),
                    order.getQuantity(), order.getPrice(),
                    order.getPrice() * order.getQuantity());
            total += order.getPrice() * order.getQuantity();
            
        }
        System.out.println("Total: " + total);
    }

    //if order exist then update order
    public void updateOrder(ArrayList<Order> lo, String id, int quantity) {
        for (Order order : lo) {
            if (order.getId().equalsIgnoreCase(id)) {
                order.setQuantity(order.getQuantity() + quantity);
                return;
            }
        }
    }
}
