/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package businesslogic;

import dataaccess.NotesDBException;
import dataaccess.UserDB;
import domainmodel.*;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.MessagingException;
import javax.naming.NamingException;

/**
 *
 * @author awarsyle
 */
public class AccountService {

    public User checkLogin(String username, String password, String path) {
        User user;

        UserDB userDB = new UserDB();
        try {
            user = userDB.getUser(username);

            if (user.getPassword().equals(password)) {
                // successful login
                Logger.getLogger(AccountService.class.getName())
                        .log(Level.INFO,
                                "A user logged in: {0}", username);
                String email = user.getEmail();
                try {
                    // WebMailService.sendMail(email, "NotesKeepr Login", "Big brother is watching you!  Hi " + user.getFirstname(), false);

                    HashMap<String, String> contents = new HashMap<>();
                    contents.put("firstname", user.getFirstname());
                    contents.put("date", ((new java.util.Date()).toString()));

                    try {
                        WebMailService.sendMail(email, "NotesKeepr Login", path + "/emailtemplates/login.html", contents);
                    } catch (IOException ex) {
                        Logger.getLogger(AccountService.class.getName()).log(Level.SEVERE, null, ex);
                    }

                } catch (MessagingException ex) {
                    Logger.getLogger(AccountService.class.getName()).log(Level.SEVERE, null, ex);
                } catch (NamingException ex) {
                    Logger.getLogger(AccountService.class.getName()).log(Level.SEVERE, null, ex);
                }

                return user;
            }

        } catch (NotesDBException ex) {
        }

        return null;
    }

    public boolean resetPassword(String email, String path, String url) throws Exception {

        UserDB db = new UserDB();

        try {
            User user = db.getUserByEmail(email);
            if (user != null) {
                String uuid = UUID.randomUUID().toString();
                url = url + "?uuid=" + uuid;

                user.setResetPasswordUUID(uuid);
                db.update(user);
                
                HashMap<String, String> map = new HashMap<>();
                map.put("link", url);
                map.put("firstname", user.getFirstname());
                map.put("date", new Date().toString());

                try {
                    WebMailService.sendMail(email, "NotesKeepr - Password Reset", path + "/emailtemplates/passwordReset.html", map);

                } catch (Exception e) {
                    Logger.getLogger(AccountService.class.getName()).log(Level.SEVERE, null, e);
                }
            }
        } catch (NotesDBException ex) {
            Logger.getLogger(AccountService.class.getName()).log(Level.SEVERE, null, ex);
        }

        return false;
    }
    
    public boolean changePassword(String uuid, String password) throws Exception{
            
        UserDB db = new UserDB();
        
        System.out.println(uuid + "hello");
        
        User user = db.getUserByUUID(uuid);
        user.setPassword(password);
        
        if(user != null){
            
            int update = db.update(user);
            
            if(update == 1){
                return true;
            }
            else{
                return false;
            }
        }
        return false;
    }

}
