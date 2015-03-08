## Author Thomas Bracher "thomas.bracher@cpe.fr"
require 'test/unit'
require 'selenium-webdriver'
require_relative 'MonthTest'

class AcadMiniTest
    class Unit < MiniTest::Unit

        def before_suites
            # code to run before the first test
            selenium_url = nil
            capabilities = nil
            if ENV['TRAVIS']
                selenium_url = 'http://' + ENV['SAUCE_USERNAME'] + ':' + ENV['SAUCE_ACCESS_KEY'] + '@ondemand.saucelabs.com:80/wd/hub'
                capabilities = Selenium::WebDriver::Remote::Capabilities.firefox
                capabilities['tunnel-identifier'] = ENV['TRAVIS_JOB_NUMBER']
                capabilities.version = '36.0'
                capabilities.platform = 'Windows 7'
                capabilities['name'] = 'Travis ' + ENV['TRAVIS_JOB_NUMBER']
                $appli_url = 'http://localhost:8080'
            elsif ENV['IN_DOCKER']
                selenium_url = 'http://selenium:4444/wd/hub'
                capabilities = :chrome
                $appli_url = 'http://172.17.42.1:' + (ENV['APPLI_URL'].split ':').last
            else
                selenium_url = 'http://localhost:4444/wd/hub'
                capabilities = :firefox
                $appli_url = 'http://localhost:8080'
            end

            $driver = Selenium::WebDriver.for(:remote, :url => selenium_url, :desired_capabilities => capabilities)
            $driver.manage.timeouts.implicit_wait = 1 # seconds
        end

        def after_suites
            # code to run after the last test
            $driver.get($appli_url + '/clear')
            if ENV['TRAVIS']
                $driver.quit
            else
                $driver.close
            end
        end

        def _run_suites(suites, type)
            begin
                before_suites
                super(suites, type)
                ensure
                after_suites
            end
        end

        def _run_suite(suite, type)
            begin
                suite.before_suite if suite.respond_to?(:before_suite)
                super(suite, type)
                ensure
                suite.after_suite if suite.respond_to?(:after_suite)
            end
        end
    end
end

MiniTest::Unit.runner = AcadMiniTest::Unit.new

class AcadTest
    def self.suite
        suite = Test::Unit::TestSuite.new
        suite << MonthTest.suite
        return suite
    end
end
