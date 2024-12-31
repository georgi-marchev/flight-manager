import { Link } from "react-router-dom";
import useAuth from "../hooks/useAuth";

const AuthLinks = () => {

    const { auth } = useAuth();

    return (
        <>
            {!auth.accessToken 
            ? (<Link className="nav-link" to="/login">Влез</Link>)
            : (<Link className="nav-link" to="/logout">Излез</Link>)
            }
        </>
  );
};

export default AuthLinks;
