import api from "./api";

const login = (username, password) => {
  return api
    .post("/auth/signin", {
      username,
      password,
    })
    .then((response) => {
      if (response.data.token) {
        localStorage.setItem("user", JSON.stringify(response.data));
        localStorage.setItem("token", response.data.token);
      }
      return response.data;
    });
};

const logout = () => {
  localStorage.removeItem("user");
  localStorage.removeItem("token");
};

const register = (username, email, password) => {
  return api.post("/auth/signup", {
    username,
    email,
    password,
  });
};

const getCurrentUser = () => {
  return JSON.parse(localStorage.getItem("user"));
};

const AuthService = {
  login,
  logout,
  register,
  getCurrentUser,
};

export default AuthService;
