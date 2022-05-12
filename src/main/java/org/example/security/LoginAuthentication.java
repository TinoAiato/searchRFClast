package org.example.security;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class LoginAuthentication implements Authentication {
   private final String login;

   @Override
   public String getName() {
      return login;
   }

   @Override
   public boolean isAnonymous() {
      return false;
   }
}
