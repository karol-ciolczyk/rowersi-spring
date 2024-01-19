function signupUser({
  username,
  password,
}: {
  username: string;
  password: string;
}) {
  return useFetch<string>("/api/v1/users", {
    method: "POST",
    body: { username, password },
  });
}

export default {
  signupUser,
};
