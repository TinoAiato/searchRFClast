package org.example.security;

public class AnonymousAuthentication implements Authentication {
   public static final String ANONYMOUS = "anonymous";

   @Override
   public String getName() {
      return ANONYMOUS; // ctrl + alt + c - public static final ...
   }

   @Override
   public boolean isAnonymous() {
      return true;
   }
}