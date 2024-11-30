package com.example.project.api;

import com.google.gson.annotations.SerializedName;

public class AccountSummary {
    @SerializedName("alias")
    private String alias;
    @SerializedName("balance")
    private String balance;
    @SerializedName("currency")
    private String currency;
    @SerializedName("marginRate")
    private String marginRate;
    @SerializedName("pl")
    private String pl;
    @SerializedName("NAV")
    private String nav;

    public AccountSummary(String alias, String balance, String currency, String marginRate, String pl, String nav) {
        this.alias = alias;
        this.balance = balance;
        this.currency = currency;
        this.marginRate = marginRate;
        this.pl = pl;
        this.nav = nav;
    }

    public String getAlias() { return alias; }
    public void setAlias(String alias) { this.alias = alias; }

    public String getBalance() { return balance; }
    public void setBalance(String balance) { this.balance = balance; }

    public String getCurrency() { return currency; }
    public void setCurrency(String currency) { this.currency = currency; }

    public String getMarginRate() { return marginRate; }
    public void setMarginRate(String marginRate) { this.marginRate = marginRate; }

    public String getPl() { return pl; }
    public void setPl(String pl) { this.pl = pl; }

    public String getNav() { return nav; }
    public void setNav(String nav) { this.nav = nav; }
}
