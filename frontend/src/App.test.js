import { render, screen } from '@testing-library/react';
import Info from './Info';

test('renders learn react link', () => {
  render(<Info />);
  const linkElement = screen.getByText(/learn react/i);
  expect(linkElement).toBeInTheDocument();
});
