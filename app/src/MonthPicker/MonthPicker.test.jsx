import MonthPicker from './MonthPicker';

it('display month correctly', () => {
  const monthPicker = new MonthPicker({ match: { params: { month: '2017-05' } } });
  expect(monthPicker.getCurrentMonthDisplay()).toEqual('May 2017');
});

it('links previous and next month correctly', () => {
  const monthPicker = new MonthPicker({ match: { params: { month: '2017-05' } } });
  expect(monthPicker.getPreviousMonthLink()).toEqual('/month/2017-04');
  expect(monthPicker.getNextMonthLink()).toEqual('/month/2017-06');
});
