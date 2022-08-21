import React from "react";
import { HashRouter, Route, Switch, Redirect } from "react-router-dom";

// components
import Layout from "./Layout";

// pages
import Error from "../pages/error";
import Login from "../pages/login";

// context
import { useUserState } from "../context/UserContext";

export default function App() {
  // global
  var { isAuthenticated } = useUserState();
  
  console.log("===== App.js=====");

  return (
    <HashRouter>
      <Switch>
      	<Route exact path="/" render={() => <Redirect to="/app/dashboard" />} />
        <Route exact path="/login_succase" render={() => <Route to="/app/dashboard" />} />
        <Route
          exact
          path="/app"
          render={() => <Redirect to="/app/dashboard" />}
        />
        <PrivateRoute path="/app" component={Layout} />
        <PublicRoute path="/login" component={Login} />
        <Route component={Error} />
      </Switch>
    </HashRouter>
  );

  // #######################################################################

  function PrivateRoute({ component, ...rest }) {
	console.log("App_PrivateRoute : ");
	
    return (
      <Route
        {...rest}
        render={props =>
          isAuthenticated ? (
			alert("App.js_App_PrivateRoute_props : " + JSON.stringify(props) ),
            React.createElement(component, props)
          ) : (
            <Redirect
              to={{
                pathname: "/login",
                state: {
                  from: props.location,
                },
              }}
            />
          )
        }
      />
    );
  }

  function PublicRoute({ component, ...rest }) {
	console.log("App_PublicRoute : ");
	
    return (
      <Route
        {...rest}
        render={props =>
          isAuthenticated ? (
			alert("App.js_PublicRoute_props : " + JSON.stringify(props) ),
			props.history.action === "PUSH" || props.history.action === "POP" ?
            <Redirect
              to={{
                pathname: "/login_succase",
                state: {
                  from: props,
                },
                match: {
				  props: {
					year: props.history.first_year,
				  }
				},
              }}
            />
            :
            <Redirect
              to={{
                pathname: "/",
              }}
            />
          ) : (
            React.createElement(component, props)
          )
        }
      />
    );
  }
}