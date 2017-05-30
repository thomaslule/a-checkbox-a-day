import MonthPicker from "./MonthPicker";

it( "display month correctly", () => {
    let monthPicker = new MonthPicker( { match: { params: { month: "2017-05" } } } );
    expect(monthPicker.getCurrentMonthDisplay()).toEqual("May 2017");
} );

it( "links previous and next month correctly", () => {
    let monthPicker = new MonthPicker( { match: { params: { month: "2017-05" } } } );
    expect(monthPicker.getPreviousMonthCode()).toEqual("2017-04");
    expect(monthPicker.getNextMonthCode()).toEqual("2017-06");
} );
