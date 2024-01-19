"ROLE_USER";

import type { Authorities } from "./Authorities";

export type SessionUserDetails = {
  password: null;
  username: string;
  authorities: Authority[];
  accountNonExpired: boolean;
  accountNonLocked: boolean;
  credentialsNonExpired: boolean;
  enabled: boolean;
};

type Authority = {
  authority: Authorities;
};
