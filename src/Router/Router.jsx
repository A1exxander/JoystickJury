import React from 'react';
import { RouterProvider, createBrowserRouter } from 'react-router-dom';
import HomePage from '../pages/HomePage.jsx';
import GamePage from '../pages/GamePage.jsx';
import AboutPage from '../pages/AboutPage.jsx';
import ErrorPageNotFound from '../pages/ErrorPageNotFound.jsx'; 

export default function Router() {

    const router = createBrowserRouter([
      {
        path: "/",
        element: <HomePage />,
        errorElement: <ErrorPageNotFound />,
      },
      {
        path: "/about",
        element: <AboutPage />,
        errorElement: <ErrorPageNotFound />,
      },
      {
        path: "/games/:gameID",
        element: <GamePage />, 
        errorElement: <ErrorPageNotFound />, 
      },
      {
        path: "/search/:q",
        element: <ErrorPageNotFound />, 
        errorElement: <ErrorPageNotFound />, 
      },
    ]);
  
    return <RouterProvider router={router} />;

  }