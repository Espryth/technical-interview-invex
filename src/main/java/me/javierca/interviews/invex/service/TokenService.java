package me.javierca.interviews.invex.service;

import me.javierca.interviews.invex.model.token.Token;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface TokenService {

  @NotNull Token generateToken();

  @Nullable Token getToken(final @NotNull String rawToken);

}
