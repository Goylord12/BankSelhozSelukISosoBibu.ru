package com.company;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Account account = new Account();
        Deposit deposit = new Deposit(account);
        Withdraw wtd = new Withdraw(account);
        new Thread(deposit).start();
        new Thread(wtd).start();
    }
}

class Account extends Thread{

    private int balanceCryptoCards = 228;
    private int balanceZamorozka = 2300;
    int spisanieAdminu = 0;

    public synchronized void toDeposit(){
        while(balanceCryptoCards >= balanceZamorozka){

            try{
                wait();
            }
            catch (Exception ex){
                ex.getMessage();
            }
        }
        balanceCryptoCards += 100;
        System.out.println("Баланс пополнен на 100." + "\t" + "Баланc: " + balanceCryptoCards);

        notify();
    }

    public synchronized void toWithdraw(){
        while(balanceCryptoCards < balanceZamorozka){

            try {
                wait();
            }
            catch (Exception ex){
                ex.getMessage();
            }
        }

        System.out.println("Введите сумму вывода: ");
        Scanner in = new Scanner(System.in);
        spisanieAdminu = in.nextInt();
        balanceCryptoCards -= spisanieAdminu;
        if(balanceCryptoCards < 0){
            System.out.println("Решил Админа обракинуть, жди гостей");
            System.exit(0);
        }
        System.out.println("С карты списано " + spisanieAdminu + "\t" + "Баланс: " + balanceCryptoCards);

        notify();
    }
}

class Withdraw extends Thread{
    Account account;
    Withdraw(Account account){
        this.account=account;
    }
    public void run(){
        for (int i = 1; i < 50; i++) {
            account.toDeposit();
        }
    }
}
class Deposit extends Thread{

    Account account;
    Deposit(Account account){
        this.account=account;
    }
    public void run(){
        for (int i = 1; i < 50; i++) {
            account.toWithdraw();
        }
    }
}