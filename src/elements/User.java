/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package elements;

import blogviewerdata.ZoneEvent;
import blogviewerdata.VentanaInfo;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import javax.swing.JPanel;

/**
 *
 * @author leonardo
 */
public class User {
    public int id;
    public String name;
    public String username;
    public String email;
    public Address address;
    public String phone;
    public String website;
    public Company company;

    public User(int id, String name, String username, String email, Address address, String phone, String website, Company company) {
        this.id = id;
        this.name = name;
        this.username = username;
        this.email = email;
        this.address = address;
        this.phone = phone;
        this.website = website;
        this.company = company;
    }
    
    public void pintar(Graphics g, int x, int y, VentanaInfo venInfo) {
        g.setFont(new Font("Tahoma", Font.BOLD, 16));
        g.setColor(new Color(51,51,51));
        g.fillRect(x, y, 680, 40);
        g.setColor(Color.WHITE);
        g.drawString(this.username, x + 55, y + 20);
        g.setColor(new Color(243,243,243));
        g.fillRect(x + 50, y + 25, 630, 110);
        User u=this;
        JPanel panel=venInfo.getPanel();
        panel.add(new ZoneEvent(x+50, y+25, 630, 110){
            @Override
            public void respuesta(java.awt.event.MouseEvent evt){
                venInfo.showUser(u);
            }
        });
        g.setColor(Color.WHITE);
        g.drawRect(x + 10, y + 5, 30, 25);
        g.setFont(new Font("Tahoma", Font.BOLD, 14));
        g.drawString(this.id+"", x+25-(String.valueOf(this.id).length()-1)*6, y+20);
        g.setColor(Color.BLACK);
        g.drawString("Name: "+this.name, x + 60, y + 45);
        g.drawString("Email: "+this.email, x + 60, y + 65);
        g.drawString("Phone: "+this.phone, x + 60, y + 85);
        g.drawString("Company: "+this.email, x + 60, y + 105);
        g.drawString("City: "+this.email, x + 60, y + 125);
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public Address getAddress() {
        return address;
    }

    public String getPhone() {
        return phone;
    }

    public String getWebsite() {
        return website;
    }

    public Company getCompany() {
        return company;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public void setCompany(Company company) {
        this.company = company;
    }
    
    public String getCompanyName(){
        return company.name;
    }
    
    public String getCompanyCatchPhrase(){
        return company.catchPhrase;
    }
    
    public String getCompanyBs(){
        return company.bs;
    }
    
    public String getAddressStreet(){
        return address.street;
    }
    
    public String getAddressSuite(){
        return address.suite;
    }
    
    public String getAddressCity(){
        return address.city;
    }
    
    public String getAddressZipcode(){
        return address.zipcode;
    }
    
    public String getAddressGeolocalizationLat(){
        return address.geo.lat;
    }
    
    public String getAddressGeolocalizationLng(){
        return address.geo.lng;
    }
}