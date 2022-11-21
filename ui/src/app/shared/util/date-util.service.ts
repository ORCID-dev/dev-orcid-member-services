import { Injectable } from '@angular/core';
import * as moment from 'moment';
import { DEFAULT_EARLIEST_YEAR } from 'app/shared/constants/orcid-api.constants';

@Injectable({ providedIn: 'root' })
export class DateUtilService {
    getYearsList(futureYears?: number) {
        const years = [];
        const dateStart = moment(DEFAULT_EARLIEST_YEAR, 'YYYY');
        const dateEnd = moment().add(futureYears - 1, 'y');

        while (dateEnd.diff(dateStart, 'years') >= 0) {
        years.push(dateStart.format('YYYY'));
        dateStart.add(1, 'year');
        }
        return(years.reverse());
    }

    getMonthsList() {
        const months = moment.months();
        const monthsArray = [];
        for (let _i = 1; _i <= months.length; _i++) {
            monthsArray.push([('0' + _i.toString()).slice(-2), months[_i - 1]]);
        }
        return(monthsArray);
    }

    getDaysList(year?: string, month?: string) {
        let daysInMonth;
        if (year && month) {
            daysInMonth = moment(year + '-' + month, 'YYYY-MM').daysInMonth();
        } else {
            daysInMonth = moment().daysInMonth();
        }
        const days = [];
        for (let _i = 1; _i <= daysInMonth; _i++) {
            days.push(('0' + _i.toString()).slice(-2));
        }
        return(days);
    }
}
