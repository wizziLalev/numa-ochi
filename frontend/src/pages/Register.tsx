import React, { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import apiClient from '../api/axiosConfig';
import { Button } from '../components/ui/button';
import { Input } from '../components/ui/input';
import { useToast } from "../components/ui/use-toast";

const Register: React.FC = () => {
  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');
  const [passwordErrors, setPasswordErrors] = useState<string[]>([]);
  const navigate = useNavigate();
  const { toast } = useToast();

  useEffect(() => {
    const errors: string[] = [];
    if (password.length > 0) { // Only validate if user has started typing
      if (password.length < 6) errors.push("Password must be at least 6 characters long.");
      if (password.length > 12) errors.push("Password must be no more than 12 characters long.");
      if (!/[A-Z]/.test(password)) errors.push("Password must contain at least one capital letter.");
      if (!/[!@#$&*]/.test(password)) errors.push("Password must contain at least one special character (!@#$&*).");
    }
    setPasswordErrors(errors);
  }, [password]);

  const handleRegister = async (e: React.FormEvent) => {
    e.preventDefault();
    if (passwordErrors.length > 0) {
      toast({
        variant: "destructive",
        title: "Invalid Password",
        description: "Please fix the errors before submitting.",
      });
      return;
    }

    try {
      await apiClient.post('/api/register', { username, password });
      toast({
        title: "Success!",
        description: "Registration successful! You can now log in.",
      });
      navigate('/login');
    } catch (error: any) {
      const errorMsg = error.response?.data?.password || error.response?.data || 'Registration failed.';
      toast({
        variant: "destructive",
        title: "Registration Failed",
        description: errorMsg,
      });
    }
  };

  return (
    <div className="flex items-center justify-center min-h-screen bg-gray-100">
      <div className="px-8 py-6 mt-4 text-left bg-white shadow-lg">
        <h3 className="text-2xl font-bold text-center">Register</h3>
        <form onSubmit={handleRegister}>
          <div className="mt-4">
            <div>
              <label className="block" htmlFor="username">Username</label>
              <Input
                type="text"
                placeholder="Username"
                value={username}
                onChange={(e) => setUsername(e.target.value)}
                required
              />
            </div>
            <div className="mt-4">
              <label className="block" htmlFor="password">Password</label>
              <Input
                type="password"
                placeholder="Password"
                value={password}
                onChange={(e) => setPassword(e.target.value)}
                required
              />
              {passwordErrors.length > 0 && (
                <ul className="mt-2 text-sm text-red-600 space-y-1">
                  {passwordErrors.map((error, index) => (
                    <li key={index}>- {error}</li>
                  ))}
                </ul>
              )}
            </div>
            <div className="flex items-baseline justify-between">
              <Button type="submit" className="mt-4" disabled={password.length > 0 && passwordErrors.length > 0}>
                Register
              </Button>
            </div>
          </div>
        </form>
      </div>
    </div>
  );
};

export default Register;
